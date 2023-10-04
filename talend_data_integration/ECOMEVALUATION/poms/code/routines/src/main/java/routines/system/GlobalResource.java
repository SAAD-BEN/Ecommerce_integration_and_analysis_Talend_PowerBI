// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package routines.system;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalResource {

	// let it support the top level Object
	public static Map<Object, Object> resourceMap = new HashMap<Object, Object>();

	// when there is multiple threads wants to insert stats&logs&meta into DB, it is
	// used as a locker. bug:22677
	public static TalendMultiThreadLockMap resourceLockMap = new TalendMultiThreadLockMap();

	public static class TalendMultiThreadLockMap {

		private Map<Object, Object> tMultiTheadLockMap = new ConcurrentHashMap<>();

		public Object get(Object key) {
			return tMultiTheadLockMap.computeIfAbsent(key, k -> new Object());
		}
	}
}
