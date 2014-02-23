package cn.zhuhongqing.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.zhuhongqing.lucene.Lucene3;
import cn.zhuhongqing.lucene.domain.Demand;

public class DocumentTest {

	public static String indexStore = "paster";

	public static void main(String[] args) {

		List<Demand> demandList = new ArrayList<Demand>();

		for (int i = 0; i < 50; i++) {

			Demand demand = new Demand();

			demand.setDemand_id("你好中国" + i);

			demand.setDemand_title("中国人民万岁" + i);

			demand.setDemand_context("我是一个中国人" + i);

			demandList.add(demand);
		}

		String indexPath = System.getProperty("user.dir") + "\\" + indexStore;

		Lucene3 luceneCore = new Lucene3();

		luceneCore.createLuceneOption(indexStore, indexPath, 25);

		luceneCore.createIndexDefault(indexStore, demandList);

		luceneCore.RefreshLuceneRamIndex();

		Map<Demand, Float> demandMap = luceneCore.searchAllInRam(indexStore);

		/*
		 * Map<Demand, Float> demandMap = luceneCore.searchIndexInRamDefault(
		 * indexStore, "中国", "demand_title");
		 */

		/*
		 * luceneCore.deleteIndexInFile(indexStore,
		 * QueryFactory.createUniquQuery("demand_id", "你好中国1"));
		 */

		/*
		 * Map<Demand, Float> demandMap = luceneCore.searchTitleInRamPages(
		 * indexStore, "中国", 3, 7);
		 */

		/*
		 * Map<Demand, Float> demandMap = luceneCore.searchIndexInRamPages(
		 * indexStore, "中国", 0, 5, "demand_context");
		 */

		/*
		 * Map<Demand, Float> demandMap = luceneCore.searchIndexInRamDefault(
		 * indexStore, QueryFactory.createAllQuery());
		 */

		System.out.println(demandMap.size());
		Iterator<Entry<Demand, Float>> it = demandMap.entrySet().iterator();

		while (it.hasNext()) {
			Entry<Demand, Float> entry = it.next();

			System.out.println(entry.getValue());

			Demand d = entry.getKey();

			System.out.println(d);

			System.out.println(d.getDemand_id());

			System.out.println(d.getDemand_title());

			System.out.println(d.getDemand_context());
		}
	}
}
