/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2012 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.workflow.common;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.ephesoft.dcma.core.common.BatchInstanceStatus;
import com.ephesoft.dcma.da.domain.BatchInstance;

/**
 * This class sorts the batch instance list in following batch instance status order: BatchInstanceStatus.READY,
 * BatchInstanceStatus.LOCKED, BatchInstanceStatus.NEW.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.core.common.BatchInstanceStatus
 * @see com.ephesoft.dcma.da.domain.BatchInstance
 */
public class FifoPickUpComparator implements Comparator<BatchInstance> {

	/**
	 * Instance of java.util.Map<BatchInstanceStatus, Integer> to store the priority of batch instance status.
	 */
	private Map<BatchInstanceStatus, Integer> batchStatusToPriorityMap = null;

	/**
	 * Constructor
	 */
	public FifoPickUpComparator() {
		populateBIPriorityMap();
	}

	private void populateBIPriorityMap() {
		batchStatusToPriorityMap = new HashMap<BatchInstanceStatus, Integer>();
		batchStatusToPriorityMap.put(BatchInstanceStatus.READY, 1);
		batchStatusToPriorityMap.put(BatchInstanceStatus.LOCKED, 2);
		batchStatusToPriorityMap.put(BatchInstanceStatus.NEW, 3);
	}

	public FifoPickUpComparator(Map<BatchInstanceStatus, Integer> batchStatusToPriorityMap) {
		this.batchStatusToPriorityMap = batchStatusToPriorityMap;
	}

	@Override
	public int compare(final BatchInstance batchInstance1, final BatchInstance batchInstance2) {
		int returnVal = -1;
		Integer statusPriority1 = null;
		Integer statusPriority2 = null;
		if (batchInstance1 != null && batchInstance2 != null) {
			int batchInstancePriority1 = batchInstance1.getPriority();
			int batchInstancePriority2 = batchInstance2.getPriority();
			if (batchInstancePriority1 == batchInstancePriority2) {
				statusPriority1 = batchStatusToPriorityMap.get(batchInstance1.getStatus());
				statusPriority2 = batchStatusToPriorityMap.get(batchInstance2.getStatus());
			}
			if (statusPriority1 != null && statusPriority2 != null) {
				returnVal = statusPriority1.compareTo(statusPriority2);
			}
			if (returnVal == 0) {
				returnVal = -1;
			}
		}
		return returnVal;
	}

}
