import java.util.Comparator;

public class ScoreEntry implements Comparable<ScoreEntry> {

    private String name;
    private String score;

    public ScoreEntry(String name, String score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%s %s%n", name, score);
    }

    @Override
    public int compareTo(ScoreEntry o) {
        Comparator<ScoreEntry> comp = Comparator.comparing(ScoreEntry::getScore).reversed();
        return comp.compare(this, o);
    }

    public String getScore() {
        return score;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreEntry other = (ScoreEntry) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}

}
