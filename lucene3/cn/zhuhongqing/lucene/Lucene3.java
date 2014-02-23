package cn.zhuhongqing.lucene;

import java.util.Map;

import cn.zhuhongqing.forbean.DocumentBean;
import cn.zhuhongqing.lucene.domain.Demand;

public class Lucene3 extends LuceneSupport<Demand> {

	public Lucene3() {
		documentBean = new DocumentBean();

		documentBean.setDocumentBean("demand_id", true,
				DocumentBean.INDEX_NOT_ANALYZED);

		documentBean.setDocumentBean("demand_title", false,
				DocumentBean.INDEX_ANALYZED);

		documentBean.setDocumentBean("demand_context", false,
				DocumentBean.INDEX_ANALYZED);
	}

	public Map<Demand, Float> searchByIdInRam(String indexPoolName,
			String demandId) {

		return searchUniquInRam(indexPoolName, "demand_id", demandId);
	}

	public Map<Demand, Float> searchInAllInRam(String indexPoolName,
			String searchText) {
		return searchIndexInRamDefault(indexPoolName, searchText,
				"demand_title", "demand_context");
	}

	public Map<Demand, Float> searchInTitleInRam(String indexPoolName,
			String searchText) {
		return searchIndexInRamDefault(indexPoolName, searchText,
				"demand_title");
	}

	public Map<Demand, Float> searchInContextInRam(String indexPoolName,
			String searchText) {
		return searchIndexInRamDefault(indexPoolName, searchText,
				"demand_context");
	}

	public Map<Demand, Float> searchTitleInRamPages(String indexPoolName,
			String searchText, int firstPage, int lastPage) {

		return searchIndexInRamPages(indexPoolName, searchText, firstPage,
				lastPage, 0, "demand_title");

	}

	public Map<Demand, Float> searchContextInRamPages(String indexPoolName,
			String searchText, int firstPage, int lastPage) {

		return searchIndexInRamPages(indexPoolName, searchText, firstPage,
				lastPage, 0, "demand_context");

	}

	public void updateIndexInFileById(String indexPoolName, Demand demand) {

		updateIndexInFileDefaultByUnique(indexPoolName, demand,
				demand.getDemand_id(), "demand_id");
	}

	public void deleteIndexInFileById(String indexPoolName, String demandId) {

		deleteIndexInFileDefaultByUnique(indexPoolName, demandId, "demand_id");

	}
}
