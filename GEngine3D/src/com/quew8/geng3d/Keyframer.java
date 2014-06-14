package com.quew8.geng3d;

import com.quew8.geng.GameObject;
import com.quew8.geng3d.Keyframer.Key;
import com.quew8.geng.interfaces.Updateable;
import com.quew8.gutils.Clock;

/**
 * 
 * @author Quew8
 *
 * @param <T>
 * @param <K>
 */
public class Keyframer<T, K extends Key<T>> {
    private OnIntermediateAction<T, K> intermediateAction;
    private OnKeyAction<T, K> keyAction;
    private K[] keys;
    private boolean repeat;
    private boolean playOnCreated;
    
    @SafeVarargs
    public Keyframer(OnIntermediateAction<T, K> intermediateAction, OnKeyAction<T, K> keyAction, boolean repeat, boolean playOnCreated, K... keys) {
        this.intermediateAction = intermediateAction != null ? intermediateAction : new OnIntermediateAction<T, K>();
        this.keyAction = keyAction != null ? keyAction : new OnKeyAction<T, K>();
        for(int i = 0; i < keys.length; i++) {
            keys[i].setIndex(i);
        }
        this.keys = keys;
        this.repeat = repeat;
        this.playOnCreated = playOnCreated;
    }
    
    @SafeVarargs
    public Keyframer(OnIntermediateAction<T, K> intermediateAction, OnKeyAction<T, K> keyAction, boolean repeat, K... keys) {
        this(intermediateAction, keyAction, repeat, true, keys);
    }
    
    @SafeVarargs
    public Keyframer(OnIntermediateAction<T, K> intermediateAction, OnKeyAction<T, K> keyAction, K... keys) {
        this(intermediateAction, keyAction, true, keys);
    }
    
    public K getKey(int i) {
        return keys[i];
    }
    
    /**
     * 
     * @author Quew8
     *
     */
    public class KeyframerInstance extends GameObject implements Updateable {
        private final T obj;
        private int position;
        private int time;
        private boolean playing;
        
        public KeyframerInstance(T obj) {
            this.obj = obj;
            this.position = 0;
            this.time = 0;
            this.playing = playOnCreated;
        }
        
        @Override
        public void update() {
            if(playing) {
                time += Clock.getDelta();
                if(time >= keys[position].getDuration()) {
                    nextKey();
                } else {
                    int i = position+1;
                    intermediateAction.onIntermediate(
                            obj, 
                            keys[position], 
                            i < keys.length ? keys[i] : keys[0], 
                            time
                            );
                }
            }
        }
        
        public void nextKey() {
            time -= keys[position].getDuration();
            position++;
            if(position == keys.length) {
                if(repeat) {
                    position = 0;
                } else {
                    stop();
                }
            }
            int i = position + 1;
            keyAction.onKey(
                    obj, 
                    position == 0 ? keys[keys.length-1] : keys[position - 1], 
                    keys[position], 
                    i < keys.length ? keys[i] : keys[0]
                    );
        }
        
        public void play() {
            playing = true;
        }
        
        public void pause() {
        	playing = false;
        }
        
        public void playPause() {
        	if(playing) {
        		pause();
        	} else {
        		play();
        	}
        }
        
        public void stop() {
        	playing = false;
        	time = 0;
        	position = 0;
        }
    }
    
    /**
     * 
     * @author Quew8
     *
     * @param <T> The type of the object.
     */
    public static class Key<T> {
        private final int duration;
        private int index;
        
        public Key(int duration) {
            this.duration = duration;
        }
        
        public int getDuration() {
            return duration;
        }
        
        public int getIndex() {
            return index;
        }
        
        public void setIndex(int index) {
            this.index = index;
        }
    }
    
    /**
     * 
     * @author Quew8
     *
     * @param <T> The type of the object.
     * @param <E> The type of the generic parameter.
     */
    public static class GenericKey<T, E> extends Key<T> {
        private E element;
        
        public GenericKey(int duration, E element) {
            super(duration);
            this.element = element;
        }
        
        public E getElement() {
            return element;
        }
        
        public void setElement(E element) {
            this.element = element;
        }
    }
    
    /**
     * 
     * @author Quew8
     *
     * @param <T> The type of the object.
     * @param <K> The type of the key.
     */
    public static class OnIntermediateAction<T, K extends Key<T>> {
        public void onIntermediate(T obj, K lastKey, K nextKey, int time) {}
    }
    
    /**
     * 
     * @author Quew8
     *
     * @param <T> The type of the object.
     * @param <K> The type of the key.
     */
    public static class OnKeyAction<T, K extends Key<T>> {
        public void onKey(T obj, K lastKey, K currentKey, K nextKey) {}
    }
}
