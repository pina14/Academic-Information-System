package pt.isel.ls.MethodTests;

import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.Commands.CourseManagement.GetCourses;
import pt.isel.ls.Model.Commands.CourseManagement.GetCoursesAcr;
import pt.isel.ls.Model.Commands.CourseManagement.PostCourses;
import pt.isel.ls.Model.CustomExceptions.HeadersMismatchException;
import pt.isel.ls.Model.CustomExceptions.NotAvailableCommandException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.NodeCommand;
import pt.isel.ls.Model.Router;
import pt.isel.ls.Model.Utils;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MethodsTests {
    private static Router r;

    @BeforeClass
    public static void init(){
        r = new Router();
        NodeCommand base = new NodeCommand();
        NodeCommand curr;

        /* POST/courses command */
        curr = addChild("POST", base);
        curr = addChild("courses", curr);
        curr.setCommandClassRef(new PostCourses());

        /* GET/courses/text/plain  command/view */
        curr = addChild("GET", base);
        curr = addChild("courses", curr);
        curr.setCommandClassRef(new GetCourses());
        r.setTree(base);
    }

    private static NodeCommand addChild(String child, NodeCommand base) {
        NodeCommand curr;
        base.addChild(child);
        curr = base.getChild(child);
        return curr;
    }

    @Test
    public void getCommandViewTest() throws NotAvailableCommandException, HeadersMismatchException {
        /**
         * Prepare the data to get the command.
         */
        CustomMap<String, String> headers = new CustomMap<>();
        headers.put("accept","text/plain");
        Command command = r.getCommand("GET","/courses",new CustomMap<>());

        /**
         * Verify if the gotten Node is the one asked.
         */
        assertEquals(GetCourses.class, command.getClass());
    }

    @Test
    public void addCommandTest(){
        /**
         * New CommandViw Node to add.
         */
        r.addCommand("GET/courses/{acr}", new GetCoursesAcr());

        /**
         * Iterate the tree until reach the new String added and verify if Node exists.
         */
        NodeCommand current = r.getTree();
        current = current.getChild("GET");
        current = current.getChild("courses");
        current = current.getChild("{acr}");
        assertNotNull(current);

        /**
         * Verifies if the current Node view and command are the ones inserted.
         */
        assertEquals(GetCoursesAcr.class, current.getCommandClassRef().getClass());

        /**
         * Retrieve the tree to its normal form.
         */
        r = null;
        init();
    }

    @Test
    public void organizeParametersTest() {
        /**
         * Verify if the returned elements are correct.
         */
        CustomMap<String, CustomList<String>> aux = Utils.organizeParameters("teste1=t1&teste2=t2&teste2=t3");
        assertEquals("t1", aux.get("teste1").get(0));
        assertEquals("t2",aux.get("teste2").get(0));
        assertEquals("t3",aux.get("teste2").get(1));
    }

    @Test
    public void organizeHeadersTest() throws HeadersMismatchException {
        /**
         * Verify if the returned elements are correct.
         */
        CustomMap<String, String> aux = Utils.organizeHeaders("accept:teste|accept-language:portugues");
        assertEquals("teste", aux.get("accept"));
        assertEquals("portugues",aux.get("accept-language"));
    }

    @Test
    public void pathIteratorTest(){
        Iterator pathIterator = r.pathIterator("teste1/teste2/teste3");

        /**
         * Verify if the returned elements are correct.
         */
        assertNotNull(pathIterator);
        assertEquals("teste1",pathIterator.next());
        assertEquals("teste2",pathIterator.next());
        assertEquals("teste3",pathIterator.next());
    }
}