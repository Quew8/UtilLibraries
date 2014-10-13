package com.quew8.geng;

import com.quew8.geng.interfaces.Disposable;
import com.quew8.geng.interfaces.Drawable;
import com.quew8.geng.interfaces.FinalDrawable;
import com.quew8.geng.interfaces.Identifiable;
import com.quew8.geng.interfaces.Updateable;
import com.quew8.gutils.collections.Bag;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class GContainer<T extends Identifiable> {
    public final Bag<Disposable> disposables = new Bag<Disposable>(Disposable.class);
    public final Bag<Drawable> drawables = new Bag<Drawable>(Drawable.class);
    public final Bag<FinalDrawable> finalDrawables = new Bag<FinalDrawable>(FinalDrawable.class);
    public final Bag<Updateable> updateables = new Bag<Updateable>(Updateable.class);

    public void drawAll() {
        for(int i = 0; i < drawables.size(); i++) {
            drawables.get(i).draw();
        }
    }

    public void drawAllFinal() {
        for(int i = 0; i < finalDrawables.size(); i++) {
            finalDrawables.get(i).draw();
        }
    }
    
    public void updateAll() {
        for(int i = 0; i < updateables.size(); i++) {
            updateables.get(i).update();
        }
    }

    public void disposeAll() {
        for(int i = 0; i < disposables.size(); i++) {
            disposables.get(i).dispose();
        }
    }
    
    public int add(T i) {
        if(i instanceof Drawable) {
            drawables.add((Drawable) i);
        }
        if(i instanceof FinalDrawable) {
            finalDrawables.add((FinalDrawable) i);
        }
        if(i instanceof Updateable) {
            updateables.add((Updateable) i);
        }
        if(i instanceof Disposable) {
            disposables.add((Disposable) i);
        }
        return i.getId();
    }

    public int[] add(T... identifiables) {
        int[] ids = new int[identifiables.length];
        for (int i = 0; i < identifiables.length; i++) {
            ids[i] = add(identifiables[i]);
        }
        return ids;
    }

    public Identifiable get(int id) {
        Identifiable i = getFrom(id, drawables);
        if(i != null) {
            return i;
        }
        i = getFrom(id, finalDrawables);
        if(i != null) {
            return i;
        }
        i = getFrom(id, updateables);
        if(i != null) {
            return i;
        }
        return getFrom(id, disposables);
    }
    
    public void remove(T t) {
        removeFrom(t, drawables);
        removeFrom(t, finalDrawables);
        removeFrom(t, updateables);
        removeFrom(t, disposables);
        t.getParent().remove(t);
    }
    
    public static void removeFrom(Identifiable i, Bag<? extends Identifiable> from) {
        int index = getIndexFrom(i.getId(), from);
        if(index != -1) {
            from.remove(index);
        }
    }
    
    public static <T extends Identifiable> T getFrom(int id, Bag<T> from) {
        int index = getIndexFrom(id, from);
        return index != -1 ? from.get(index) : null;
    }
    
    public static int getIndexFrom(int id, Bag<? extends Identifiable> from) {
        for (int i = 0; i < from.size(); i++) {
            if (from.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
    
}
