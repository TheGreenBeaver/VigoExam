public interface DataConnection {

    /**
     *
     * @return <b>false</b> if failed to load data, otherwise <b>true</b>
     * @param fromFile The file to load data from
     */
    boolean loadData(String fromFile);

    /**
     *
     * @return <b>false</b> if failed to save data, otherwise <b>true</b>
     * @param toFile The file to save data to
     */
    boolean saveData(String toFile);

}
