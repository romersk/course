package database.command;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertCommand extends Command{

    public InsertCommand(String query, Statement statement) {
        super(query, statement);
    }

    @Override
    public Statement execute() {
        try {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Command.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statement;
    }
}
