package database.command;

import java.sql.Statement;

public abstract class Command {

    protected String query;
    protected Statement statement;

    public Command(String query, Statement statement) {
        this.query = query;
        this.statement = statement;
    }

    public abstract Statement execute();
}
