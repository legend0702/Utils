package cn.zhuhongqing.forbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

public class DocumentBean {

	public static final Index INDEX_NO = Index.NO;

	public static final Index INDEX_NOT_ANALYZED = Index.NOT_ANALYZED;

	public static final Index INDEX_ANALYZED = Index.ANALYZED;

	private Map<String, StoreIndex> StoreIndexMap = new HashMap<String, StoreIndex>();

	public void setDocumentBean(String fieldName, boolean store, Index index) {

		StoreIndex si = new StoreIndex();

		si.setStore(store);

		si.setIndex(index);

		StoreIndexMap.put(fieldName, si);

	}

	public StoreIndex getStoreIndex(String fieldName) {
		return StoreIndexMap.get(fieldName);
	}

	public void clearDocumentBean() {
		StoreIndexMap.clear();
	}

	public class StoreIndex {

		private Store store;

		private Index index;

		public Store getStore() {
			return store;
		}

		public void setStore(boolean store) {
			if (store) {
				this.store = Store.YES;
			} else {
				this.store = Store.NO;
			}
		}

		public Index getIndex() {
			return index;
		}

		public void setIndex(Index index) {
			this.index = index;
		}

	}

}
