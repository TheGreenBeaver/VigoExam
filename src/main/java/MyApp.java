import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyApp implements DataConnection {

    private static MyApp instance = null;

    private String year;
    private float lastCalculatedStatistics = 0;

    private static final int START_YEAR = 1990;
    private static final int END_YEAR = 2020;

    private static final int DEFAULT_YEAR = 1990;
    private static final String DEFAULT_LOAD_FILE = "Data.txt";
    private static final String DEFAULT_SAVE_FILE = "Statistics.txt";

    /**
     *
     * @see MyApp#getInstance()
     * @return Singleton MyApp instance
     */
    public static MyApp getInstance(int currentYear){
        if (instance == null) {
            instance = new MyApp(currentYear);
        }
        return instance;
    }

    /**
     *
     * @see MyApp#getInstance(int)
     */
    public static MyApp getInstance(){
        return getInstance(DEFAULT_YEAR);
    }

    private MyApp(int year) {
        this.year = String.valueOf(year);
    }

    /**
     * @param args First parameter is <b>fromFile</b>, second parameter is <b>toFile</b>
     *             If no parameters provided, defaults to
     *             {@link MyApp#DEFAULT_LOAD_FILE} and {@link MyApp#DEFAULT_SAVE_FILE}
     */
    public static void main(String[] args) {

        String fromFile = args.length >= 1 ? args[0] : DEFAULT_LOAD_FILE;
        String toFile = args.length == 2 ? args[1] : DEFAULT_SAVE_FILE;

        boolean success = false;

        for (int currentExploredYear = START_YEAR; currentExploredYear < END_YEAR; currentExploredYear++) {

            success = getInstance(currentExploredYear).loadData(fromFile);

            if (success) {

                if (getInstance(currentExploredYear).lastCalculatedStatistics > 0) {
                    System.out.println(
                            currentExploredYear +
                            " " +
                            getInstance(currentExploredYear).lastCalculatedStatistics);
                }
                success = getInstance(currentExploredYear).saveData(toFile);

            }

            if (!success) {
                break;
            }
        }

        if (success) {
            System.out.println("Data has been successfully loaded and saved");
        }
    }

    /**
     *
     * @see DataConnection#loadData(String)
     * An empty file isn't considered a failure, still returns <b>true</b> while sending a warning message
     */
    public boolean loadData(String fromFile) {

        try(FileInputStream fileInputStream = new FileInputStream(fromFile)) {

            int fileSize = fileInputStream.available();
            byte[] byteData = new byte[fileSize];
            int bytesReadAmount = fileInputStream.read(byteData);

            if (bytesReadAmount == -1) {
                System.out.println(fromFile + " is empty, statistics is still at 0");
                return true;
            }

            String fileDataAsPlainText = new String(byteData);
            String[] lines = fileDataAsPlainText.split("\n");

            float sum = 0;
            int yearAppearanceCount = 0;

            for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {

                String line = lines[lineIndex];
                String[] splitBySpace = line.split(" ");

                if (splitBySpace.length > 3) {

                    String date = splitBySpace[2];

                    if (date.contains(year)) {
                        String statisticsInfo = splitBySpace[3];
                        try {
                            sum += Float.parseFloat(statisticsInfo);
                            yearAppearanceCount++;
                        } catch (NumberFormatException e) {
                            System.out.println("Illegal statistics information in line " + lineIndex);
                            return false;
                        }
                    }

                }

            }

            lastCalculatedStatistics = sum / yearAppearanceCount;
            return true;

        } catch (IOException | SecurityException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    /**
     *
     * @see DataConnection#saveData(String)
     */
    public boolean saveData(String toFile){
        try(FileOutputStream fileOutputStream = new FileOutputStream(toFile, true)) {
            String toSave = year + " " + lastCalculatedStatistics + "\n";
            fileOutputStream.write(toSave.getBytes());
            return true;
        } catch (IOException | SecurityException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
