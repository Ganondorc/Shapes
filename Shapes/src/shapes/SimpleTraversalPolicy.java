/*
 * Name: Michael Frake
 * Project: CMSC 3 Project 1
 * Date: Sep 14, 2021
 * Description: 
 */
package shapes;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Arrays;
import java.util.List;

class SimpleFocusTraversalPolicy extends FocusTraversalPolicy {
	private final List<? extends Component> order;

	public SimpleFocusTraversalPolicy(Component... arrays) {
		super();
		this.order = Arrays.asList(arrays);
	}

	public SimpleFocusTraversalPolicy(List<? extends Component> list) {
		order = list;
		//System.out.println(order.size());
		//order.forEach(tabber -> System.out.println(tabber.getClass().getSimpleName()));
	}
	
	@Override
	public Component getFirstComponent(Container focusCycleRoot) {
		return order.get(0);
	}

	@Override
	public Component getLastComponent(Container focusCycleRoot) {
		return order.get(order.size() - 1);
	}

	@Override
	public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
		int idx = order.indexOf(aComponent) + 1;
		if (idx >= order.size() && !focusCycleRoot.isFocusCycleRoot()) {
			return null;
		} else {
			return order.get(idx % order.size());
		}
	}

	@Override
	public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
		int idx = order.indexOf(aComponent) - 1;
		if (idx < 0 && !focusCycleRoot.isFocusCycleRoot()) {
			return null;
		} else {
			return order.get((idx + order.size()) % order.size());
		}
	}

	@Override
	public Component getDefaultComponent(Container focusCycleRoot) {
		return order.get(0);
	}
}
