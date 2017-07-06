package pt.isel.ls.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.Model.Router;

public interface Controller {
    SQLServerDataSource DATA_SOURCE = new SQLServerDataSource();
    Router ROUTER = new Router();
    void requestExit();
}