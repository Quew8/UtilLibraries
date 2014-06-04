package com.quew8.geng;

import com.quew8.geng.Animator.AnimatorKey;
import com.quew8.geng.Keyframer.Key;
import com.quew8.gutils.Clock;
import com.quew8.gmath.Vector;


/**
 * 
 * @author Quew8
 *
 */
public class Animator extends Keyframer<Bindable, AnimatorKey> {
	
	public Animator(boolean repeat, AnimatorKey... keys) {
		super(
                new OnIntermediateAction<Bindable, AnimatorKey>() {
					@Override
					public void onIntermediate(Bindable obj, AnimatorKey lastKey,
							AnimatorKey nextKey, int time) {
                        
						obj.translate(Vector.times(new Vector(), lastKey.dvPerTime, Clock.getDelta()));
						obj.rotate(Vector.times(new Vector(), lastKey.daPerTime, Clock.getDelta()));
					}
				},
				new OnKeyAction<Bindable, AnimatorKey>(),
				repeat,
				keys
				);
	}
	
	/**
	 * 
	 * @author Quew8
	 *
	 */
	public static class AnimatorKey extends Key<Bindable> {
		private final Vector dvPerTime;
		private final Vector daPerTime;
		
		public AnimatorKey(Vector dv, Vector da, int duration) {
			super(duration);
			this.dvPerTime = dv.divide(duration);
			this.daPerTime = da.divide(duration);
		}
	}
}
