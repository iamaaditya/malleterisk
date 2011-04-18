package unitary.execution;

import imbalance.OneVersusAll;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import types.mallet.LabeledInstancesList;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Label;

public class OneVersusAllUnitTest {
	private InstanceList instances;
	private LabeledInstancesList lil;
	
	@Before
	public void setUp() {
		this.instances = InstanceList.load(new File("instances+1+6+subjects"));
		this.lil = new LabeledInstancesList(instances);
	}
	
	@Test
	public void test() {
		OneVersusAll ova = new OneVersusAll(instances);
		
		while (ova.hasNext()) {
			InstanceList newInstances = ova.next();
			int oneCount = 0;
			int allCount = 0;
			
			for (Instance instance : newInstances) {
				Label l = (Label) instance.getTarget();
				if(l.getEntry().equals(-1)) allCount++;
				else oneCount++;
			}
			
			InstanceList oneInstances = lil.labelsInstancelists.get(ova.getCurrentOneClass());
			
			Assert.assertEquals(oneCount, oneInstances.size());
			Assert.assertEquals(allCount, instances.size()-oneInstances.size());
		}
	}
}
