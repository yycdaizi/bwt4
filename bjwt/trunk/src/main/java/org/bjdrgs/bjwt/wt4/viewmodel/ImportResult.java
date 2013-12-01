package org.bjdrgs.bjwt.wt4.viewmodel;

public class ImportResult {
	private int inserted;
	private int updated;
	private int ignored;

	public int getInserted() {
		return inserted;
	}

	public void setInserted(int inserted) {
		this.inserted = inserted;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	public int getIgnored() {
		return ignored;
	}

	public void setIgnored(int ignored) {
		this.ignored = ignored;
	}

	public void addInserted(int i) {
		inserted += i;
	}

	public void addUpdated(int i) {
		updated += i;
	}

	public void addIgnored(int i) {
		ignored += i;
	}

	public void addResult(ImportResult result) {
		inserted += result.getInserted();
		updated += result.getUpdated();
		ignored += result.getIgnored();
	}

	public int getTotal() {
		return inserted + updated + ignored;
	}
}
