package pt.isel.ls.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Controller.Controller;
import pt.isel.ls.Model.Commands.Additional.*;
import pt.isel.ls.Model.Commands.ClassManagement.GetCoursesAcrClasses;
import pt.isel.ls.Model.Commands.ClassManagement.GetCoursesAcrClassesSem;
import pt.isel.ls.Model.Commands.ClassManagement.GetCoursesAcrClassesSemNum;
import pt.isel.ls.Model.Commands.ClassManagement.PostCoursesAcrClasses;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.CourseManagement.GetCourses;
import pt.isel.ls.Model.Commands.CourseManagement.GetCoursesAcr;
import pt.isel.ls.Model.Commands.CourseManagement.PostCourses;
import pt.isel.ls.Model.Commands.ProgrammeManagement.*;
import pt.isel.ls.Model.Commands.StudentManagement.*;
import pt.isel.ls.Model.Commands.TeacherManagement.GetCoursesAcrClassesSemNumTeachers;
import pt.isel.ls.Model.Commands.TeacherManagement.GetTeachersNumClasses;
import pt.isel.ls.Model.Commands.TeacherManagement.PostCoursesAcrClassesSemNumTeachers;
import pt.isel.ls.Model.Commands.TeacherManagement.PutTeachersNum;
import pt.isel.ls.Model.Commands.UserManagement.*;
import pt.isel.ls.Model.CustomExceptions.HeadersMismatchException;
import pt.isel.ls.Model.CustomExceptions.NotAvailableCommandException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;

import java.io.Closeable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Router {
    private NodeCommand commandTree = new NodeCommand();
    private static final Logger _logger = LoggerFactory.getLogger(Router.class);

    public NodeCommand getTree(){
        return commandTree;
    }

    public void setTree(NodeCommand tree){
        commandTree = tree;
    }

    /**
     * Run through the commandTree depending on iterator and returns the command.
     * @param typeMethod Define the type of action to perform.
     * @param path Define the resource on which the command is executed.
     * @param pathParameters Accumulative HashMap to contain the path parameters.
     * @return The command represented by the sequence in the iterator.
     */
    public Command getCommand(String typeMethod, String path, CustomMap<String, String> pathParameters) throws NotAvailableCommandException, HeadersMismatchException {
        _logger.info("Beginning to process command tree in order to obtain the command.");
        /* Initiate variables. */
        String nextWord;
        NodeCommand curNode = commandTree, auxNode;
        Iterator it = pathIterator(typeMethod + path);

        /* Iterate and goes trough over the tree nodes, adding path parameters if needed, ending in the represented command node. */
        while (it.hasNext()) {
            if (!curNode.hasChildren())
                throw new NotAvailableCommandException();

            /* Get next word of the command. */
            nextWord = (String) it.next();

            /* Get next node corresponding to the next word. */
            auxNode = curNode.getChild(nextWord);

            /* If node isn't null proceeds with the new node. */
            if (auxNode != null){
                curNode = auxNode;
                continue;
            }

            /* Get the next node with variable string command. */
            curNode = curNode.getNextVariableCommand();

            /* If its null or there's no next word than the command doesn't exist. */
            if (curNode == null || nextWord.equals(""))
                throw new NotAvailableCommandException();

            /* Add the nextWord to the pathParameter customMap. */
            pathParameters.put(curNode.getCommand(), nextWord);
        }
        _logger.info("Success in obtaining the command.");
        return curNode.getCommandClassRef();
    }

    /**
     * Create a commandTree that contains all the commands possible at that time.
     * The first add of each command to it's view, the command creation must contain the description and template,
     * in order to OPTION command can be well performed.
     * @param controller The controller that this router belongs to.
     * @param closeables Custom List of closeable elements.
     */
    public void createCommandTree(Controller controller, CustomList<Closeable> closeables) {
        _logger.info("Beginning to construct command tree.");

        /* Add courses commands. */
        addCommand("GET/courses", new GetCourses("Shows all courses.", "GET /courses"));
        addCommand("GET/courses/{acr}", new GetCoursesAcr("Shows the course with the acr acronym.", "GET /courses/{acr}"));
        addCommand("GET/programmes/{pid}/courses", new GetProgrammesPidCourses("Shows the course structure of programme pid.", "GET /programmes/{pid}/courses"));
        addCommand("POST/courses", new PostCourses("Creates a new course, given the following parameters:\n" +
                                                                     "\tname - course name.\n" +
                                                                     "\tacr - course acronym.\n" +
                                                                     "\tteacher - number of the coordinator teacher.", "POST /courses"));

        addCommand("POST/programmes/{pid}/courses", new PostProgrammesPidCourses("Adds a new course to the programme pid, given the following parameters\n" +
                                                                                                   "\tacr - the course acronym.\n" +
                                                                                                   "\tmandatory - true if the course is mandatory.\n" +
                                                                                                   "\tsemesters - comma separated list of curricular semesters.", "POST /programmes/{pid}/courses"));

        /* Add classes commands. */
        addCommand("GET/courses/{acr}/classes", new GetCoursesAcrClasses("Shows all classes for a course.", "GET /courses/{acr}/classes"));
        addCommand("GET/courses/{acr}/classes/{sem}", new GetCoursesAcrClassesSem("Shows all classes of the acr course on the sem semester.", "GET /courses/{acr}/classes/{sem}"));
        addCommand("GET/courses/{acr}/classes/{sem}/{num}", new GetCoursesAcrClassesSemNum("Shows the classes of the acr course on the sem semester and with num number.", "GET /courses/{acr}/classes/{sem}/{num}"));
        addCommand("GET/teachers/{num}/classes", new GetTeachersNumClasses("Shows all teachers for a class.", "GET /courses/{acr}/classes/{sem}/{num}/teachers"));
        addCommand("POST/courses/{acr}/classes", new PostCoursesAcrClasses("Creates a new class on the course with acr acronym, given the following parameters:\n" +
                                                                                             "\tsem - semester identifier (e.g. 1415v).\n" +
                                                                                             "\tnum - class number - (e.g. D1).","POST /courses/{acr}/classes"));

        /* Add programmes commands. */
        addCommand("GET/programmes", new GetProgrammes("List all the programmes.", "GET /programmes"));
        addCommand("GET/programmes/{pid}", new GetProgrammesPid("Shows the details of programme with pid acronym.", "GET /programmes/{pid}"));
        addCommand("POST/programmes", new PostProgrammes("Creates a new programme, given the following parameters:\n" +
                                                                           "\tpid - programme acronym (e.g. \"LEIC\").\n" +
                                                                           "\tname - programme name.\n" +
                                                                             "\tlength - number of semesters.", "POST /programmes"));

        /* Add users commands. */
        addCommand("GET/users", new GetUsers("Shows all users.","GET /users"));

        /* Add teachers commands. */
        addCommand("GET/teachers", new GetTeachers("Shows all teachers.", "GET /teachers"));
        addCommand("GET/teachers/{num}", new GetTeachersNum("Shows the teacher with number num", "GET /teachers/{num}"));
        addCommand("GET/courses/{acr}/classes/{sem}/{num}/teachers", new GetCoursesAcrClassesSemNumTeachers("Shows all classes for the teacher with num number.", "GET /teachers/{num}/classes"));
        addCommand("POST/teachers", new PostTeachers("Creates a new teacher, given the following parameters:\n" +
                                                                         "\tnum - teacher number.\n" +
                                                                         "\tname- teacher name.\n" +
                                                                         "\temail - teacher email.", "POST /teachers"));

        addCommand("POST/courses/{acr}/classes/{sem}/{num}/teachers", new PostCoursesAcrClassesSemNumTeachers("Adds a new teacher to a class, given the following parameters:\n" +
                "\tnumDoc - teacher number", "POST /courses/{acr}/classes/{sem}/{num}/teachers"));
        addCommand("PUT/teachers/{num}", new PutTeachersNum("Updates an existent teacher, given all required parameters.", "PUT /teachers/{num}"));

        /* Add students commands. */
        addCommand("GET/students", new GetStudents("Shows all students.", "GET /students"));
        addCommand("GET/students/{num}", new GetStudentsNum("Shows the student with the number num.", "GET /students/{num}"));
        addCommand("GET/courses/{acr}/classes/{sem}/{num}/students", new GetCoursesAcrClassesSemNumStudents("Shows all students of a class.", "GET /courses/{acr}/classes/{sem}/{num}/students"));
        addCommand("GET/courses/{acr}/classes/{sem}/{num}/students/sorted", new GetCoursesAcrClassesSemNumStudentsSorted("Returns a list with all students of the class, ordered by increasing student number.", "GET /courses/{acr}/classes/{sem}/{num}/students/sorted"));
        addCommand("POST/students", new PostStudents("Creates a new student, given the following parameters:\n" +
                                                                         "\tnum - student number.\n" +
                                                                         "\tname- student name.\n" +
                                                                         "\temail - student email.\n" +
                                                                         "\tpid - programme acronym.", "POST /students"));

        addCommand("POST/courses/{acr}/classes/{sem}/{num}/students", new PostCoursesAcrClassesSemNumStudents("Adds a new student to a class, given the following parameter that can occur multiple times:\n\tnumStu - student number.", "POST /courses/{acr}/classes/{sem}/{num}/students"));
        addCommand("DELETE/courses/{acr}/classes/{sem}/{num}/students/{numStu}", new DeleteCoursesAcrClassesSemNumStudentsNumStu("Removes a student from a class.", "DELETE /courses/{acr}/classes/{sem}/{num}/students/{numStu}"));
        addCommand("PUT/students/{num}", new PutStudentsNum( "Updates an existent student, given all required parameters.", "PUT /students/{num}"));

        /* Add additional commands. */
        addCommand("OPTION/", new Option("Presents a list of available commands and their characteristics.", "OPTION /"));
        addCommand("EXIT/", new Exit(controller, "Ends the application.", "EXIT /"));
        addCommand("LISTEN/", new Listen("Initiate server", "LISTEN /", closeables));
        addCommand("GET/", new Root("Presents the general options available.", "GET /"));
        addCommand("GET/favicon.ico", new GetFavicon("Sets the icon for the page", "GET /favicon.ico"));
        _logger.info("Finishing with success to build command tree.");
    }

    /**
     * Split the String commandView by / and goes through the trie checking if in the current set of child's
     * exists a node with the commandView equals to the word given by the iterator, otherwise creates a new node with that word
     * int the last word of the String commandView when it creates the node it's given to it the EntityCommand commandClass
     * @param commandClass The command to be added.
     * @param command String that represents the command.
     */
    public void addCommand(String command, Command commandClass) {
        /* Initiate variables. */
        Iterator it = pathIterator(command);
        String word;
        NodeCommand node = commandTree;

        /* Add the commandView to the commands list. */
        while (it.hasNext()) {
            word = (String)it.next();
            if (node.getChild(word) == null){
                node.addChild(word);
            }
            node = node.getChild(word);
        }
        node.setCommandClassRef(commandClass);
    }

    /**
     * @param path String to be iterated.
     * @return iterator that iterates over a sentence separated by "/".
     */
    public Iterator pathIterator(String path) {
        return new Iterator() {
            boolean consumed = true;
            int idx = 0;
            String current = "";
            String[] sentences = path.split("/");

            @Override
            public boolean hasNext() {
                if(consumed){
                    if( idx < sentences.length ){
                        current = sentences[idx++];
                        consumed = false;
                        return true;
                    }
                    return false;
                } else return true;
            }
            @Override
            public String next() {
                if(!hasNext())
                    throw new NoSuchElementException();
                consumed = true;
                return current;
            }
        };
    }
}