package homeTry.team.dto;

public record RankingDTO(
        String name,
        int ranking,
        Long totalExerciseTime
) {
    public static RankingDTO of(String name, int ranking, Long totalExerciseTime) {
        return new RankingDTO(name, ranking, totalExerciseTime);
    }
}
