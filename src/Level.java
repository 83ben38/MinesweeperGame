public interface Level {

    default boolean isNextTo(int dimension1, int dimension2, int dimensionNumber){
        return true;
    }
    default boolean isNextTo(int[] dimension1, int[] dimension2){
        return true;
    }
    int mineChance();
    int size();
    int tileSize();
    int dimensions();
    boolean startingCriteria(int[] dimensions1, int[] dimensions2);
}
