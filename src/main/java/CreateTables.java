import java.io.*;

public class CreateTables {
    private static void loadFile(String fileName) {
        String statements = "";
        try {
            InputStream stream = CreateTables.class.getClassLoader().getResourceAsStream(fileName);
            InputStreamReader input = new InputStreamReader(stream);
            BufferedReader bufRead = new BufferedReader(input);
            String myLine;
            while ((myLine = bufRead.readLine()) != null)
            {
                statements += myLine + " ";
            }
            input.close();
        } catch (IOException e) {
            System.out.println("Could not load SQL file " + fileName + " "
                    + e.getMessage());
        }
        String[] createStates = statements.split(";");
        for (int i = 0; i < createStates.length - 1; i++){
            System.out.println(createStates[i]);
            String statement = createStates[i] + ";";
            if (createStates[i].toLowerCase().contains("begin")) {
                while (!(createStates[i].toLowerCase().contains("end")
                        && !createStates[i].toLowerCase().contains("if"))) {
                    statement += createStates[++i] + ";";
                }
            }
            DatabaseInterfacer.getInstance().execute(statement);
        }
    }

    public static void main(String[] args) {
        loadFile("DatabaseTables.sql");
        loadFile("TableData.sql");
    }
}