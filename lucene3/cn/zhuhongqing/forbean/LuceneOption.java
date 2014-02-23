package cn.zhuhongqing.forbean;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import cn.zhuhongqing.lucene.factory.LuceneProperty;

public class LuceneOption {

	private int firstPage;

	private int lastPage;

	private int maxResult;

	private Directory directory;

	private Analyzer analyzer;

	private Directory ramDirectory;

	private Version version;

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(String filePath) {
		if (filePath == null) {
			setDirectory(LuceneProperty.getProperty("IndexPath"));
		} else {

			File file = new File(filePath);
			try {
				this.directory = FSDirectory.open(file);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		if (analyzer == null) {
			this.analyzer = LuceneProperty.createClass(Analyzer.class);
		} else {
			this.analyzer = analyzer;
		}
	}

	public Directory getRamDirectory() {
		return ramDirectory;
	}

	public void setRamDirectory(Directory ramDirectory) {
		try {
			this.ramDirectory = new RAMDirectory(ramDirectory);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void refreshRam() {

		closeRam();

		setRamDirectory(getDirectory());
	}

	public void claseFile() {
		try {
			if (this.directory != null)
				this.directory.close();
			this.directory = null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void closeRam() {
		try {
			if (this.ramDirectory != null)
				this.ramDirectory.close();

			this.ramDirectory = null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public void refreshPages() {
		this.firstPage = 0;
		this.lastPage = 0;
	}
}
