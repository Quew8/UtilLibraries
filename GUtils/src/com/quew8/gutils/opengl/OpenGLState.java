package com.quew8.gutils.opengl;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.debug.DebugLogger;
import static com.quew8.gutils.opengl.OpenGL.*;

import com.quew8.gutils.collections.Bag;
import com.quew8.gutils.collections.Stack;
import java.nio.ByteBuffer;

public class OpenGLState {
	public static final int COLOUR_BUFFER_BIT = 1;
	public static final int DEPTH_BUFFER_BIT = 2;
	public static final int POLYGON_BIT = 4;
	public static final int SCISSOR_BIT = 8;
	public static final int STENCIL_BUFFER_BIT = 16;
	public static final int VIEWPORT_BIT = 32;
	public static final OpenGLState DEFAULT_STATE = new OpenGLState();
	
    private static final ByteBuffer buffer = BufferUtils.createByteBuffer(16);
	private static OpenGLState currentState = DEFAULT_STATE;
	private static Stack<PushedState> pushStack = new Stack<PushedState>(PushedState.class, 2);
	
	public final ColourBufferState colourBufferState;
	public final DepthBufferState depthBufferState;
	public final PolygonState polygonState;
	public final ScissorState scissorState;
	public final StencilBufferState stencilBufferState;
	public final ViewportState viewportState;
	
	public OpenGLState(ColourBufferState colourBufferState,
			DepthBufferState depthBufferState, PolygonState polygonState,
			ScissorState scissorState, StencilBufferState stencilBufferState,
			ViewportState viewportState) {
		
		this.colourBufferState = colourBufferState;
		this.depthBufferState = depthBufferState;
		this.polygonState = polygonState;
		this.scissorState = scissorState;
		this.stencilBufferState = stencilBufferState;
		this.viewportState = viewportState;
	}
	
	public OpenGLState() {
		this.colourBufferState = new ColourBufferState();
		this.depthBufferState = new DepthBufferState();
		this.polygonState = new PolygonState();
		this.scissorState = new ScissorState();
		this.stencilBufferState = new StencilBufferState();
		this.viewportState = new ViewportState();
	}
	
	public void pushState(int mask) {
		pushStack.push(new PushedState(this, mask));
	}
	
	public static void pushCurrentState(int mask) {
		currentState.pushState(mask);
	}
	
	public static void popState() {
		pushStack.pop().popState();
	}
	
	public void setState(OpenGLState prev) {
		OpenGLState.currentState = this;
		colourBufferState.setState(prev.colourBufferState);
		depthBufferState.setState(prev.depthBufferState);
		polygonState.setState(prev.polygonState);
		scissorState.setState(prev.scissorState);
		stencilBufferState.setState(prev.stencilBufferState);
		viewportState.setState(prev.viewportState);
	}
	
	public void setState() {
		setState(DEFAULT_STATE);
	}
	
	public void setAllState() {
		OpenGLState.currentState = this;
		colourBufferState.setState();
		depthBufferState.setState();
		polygonState.setState();
		scissorState.setState();
		stencilBufferState.setState();
		viewportState.setState();
	}
	
    public void takeSnapshot() {
		colourBufferState.takeSnapshot();
		depthBufferState.takeSnapshot();
		polygonState.takeSnapshot();
		scissorState.takeSnapshot();
		stencilBufferState.takeSnapshot();
		viewportState.takeSnapshot();
    }
    
    public static OpenGLState createStateFromCurrent(ColourBufferState colourBufferState,
			DepthBufferState depthBufferState, PolygonState polygonState,
			ScissorState scissorState, StencilBufferState stencilBufferState,
			ViewportState viewportState) {
		
        return new OpenGLState(
                colourBufferState == null ? 
                        currentState.colourBufferState : 
                        colourBufferState,
                depthBufferState == null ? 
                        currentState.depthBufferState : 
                        depthBufferState,
                polygonState == null ? 
                        currentState.polygonState : 
                        polygonState,
                scissorState == null ? 
                        currentState.scissorState : 
                        scissorState,
                stencilBufferState == null ? 
                        currentState.stencilBufferState : 
                        stencilBufferState,
                viewportState == null ? 
                        currentState.viewportState : 
                        viewportState
        );
	}
    
	private static class PushedState {
        public final OpenGLState state;
		public final ColourBufferState colourBufferState;
		public final DepthBufferState depthBufferState;
		public final PolygonState polygonState;
		public final ScissorState scissorState;
		public final StencilBufferState stencilBufferState;
		public final ViewportState viewportState;
		
		public PushedState(OpenGLState from, int mask) {
            this.state = from;
			this.colourBufferState = 
					(mask & COLOUR_BUFFER_BIT) != 0 ? 
					from.colourBufferState : 
					null;
			this.depthBufferState = 
					(mask & DEPTH_BUFFER_BIT) != 0 ? 
					from.depthBufferState : 
					null;
			this.polygonState = 
					(mask & POLYGON_BIT) != 0 ? 
					from.polygonState : 
					null;
			this.scissorState = 
					(mask & SCISSOR_BIT) != 0 ? 
					from.scissorState : 
					null;
			this.stencilBufferState = 
					(mask & STENCIL_BUFFER_BIT) != 0 ? 
					from.stencilBufferState : 
					null;
			this.viewportState = 
					(mask & VIEWPORT_BIT) != 0 ? 
					from.viewportState : 
					null;
		}
		
		public void popState() {
			if(colourBufferState != null) {
				colourBufferState.setState();
			}
			if(depthBufferState != null) {
				depthBufferState.setState();
			}
			if(polygonState != null) {
				polygonState.setState();
			}
			if(scissorState != null) {
				scissorState.setState();
			}
			if(stencilBufferState != null) {
				stencilBufferState.setState();
			}
			if(viewportState != null) {
				viewportState.setState();
			}
            currentState = createStateFromCurrent(
                    colourBufferState, depthBufferState, polygonState, 
                    scissorState, stencilBufferState, viewportState
            );
		}
	}
	
	private static abstract class State<T extends State<T>> {
		protected final SubState<?>[] subStates;
		
		public State(SubState<?>[] subStates) {
			this.subStates = subStates;
		}
		
		public void setState() {
			for(int i = 0; i < subStates.length; i++) {
				subStates[i].setState();
			}
		}
		
		public void setState(T from) {
			for(int i = 0; i < subStates.length; i++) {
				if(!from.subStates[i].isSameNoType(from.subStates[i])) {
					DebugLogger.log("OPENGL STATE", "Setting State: " + subStates[i].getClass().getName());
					subStates[i].setState();
				} else {
					DebugLogger.log("OPENGL STATE", "Not Setting State: " + subStates[i].getClass().getName());
				}
			}
		}
		
		public void getDifferences(T from, Bag<SubState<?>> addTo) {
			for(int i = 0; i < subStates.length; i++) {
				if(!from.subStates[i].isSameNoType(from.subStates[i])) {
					addTo.add(subStates[i]);
				}
			}
		}
        
        public void takeSnapshot() {
			for(int i = 0; i < subStates.length; i++) {
				subStates[i].loadState();
			}
            
        }
	}
	
	private static abstract class SubState<T extends SubState<T>> {
        
		public abstract void setState();
		
        public abstract void loadState();
        
		public abstract boolean isSame(T other);
		
		@SuppressWarnings("unchecked")
		private boolean isSameNoType(Object other) {
			return isSame((T) other);
		}
	}
	
	private abstract static class BooleanState<T extends BooleanState<T>> extends SubState<T> {
		protected boolean enabled;

		public BooleanState(boolean enabled) {
			this.enabled = enabled;
		}
		
		@Override
		public boolean isSame(T other) {
			return other.enabled == this.enabled;
		}
	}
	
	private abstract static class EnableState<T extends EnableState<T>> extends BooleanState<T> {
		private final int glEnum;
		
		public EnableState(boolean enabled, int glEnum) {
			super(enabled);
			if(
					glEnum != GL_BLEND && 
					glEnum != GL_CULL_FACE && 
					glEnum != GL_DEPTH_TEST && 
					glEnum != GL_DITHER && 
					glEnum != GL_POLYGON_OFFSET_FILL && 
					glEnum != GL_SAMPLE_ALPHA_TO_COVERAGE && 
					glEnum != GL_SAMPLE_COVERAGE && 
					glEnum != GL_SCISSOR_TEST && 
					glEnum != GL_STENCIL_TEST
					) {
				throw new IllegalArgumentException("glEnum is not an acceptable OpenGL enum: " + glEnum + " " + OpenGLUtils.toOpenGLEnum(glEnum));
			}
			this.glEnum = glEnum;
		}

		@Override
		public void setState() {
			if(enabled) {
				glEnable(glEnum);
			} else {
				glDisable(glEnum);
			}
		}
        
        @Override
        public void loadState() {
            enabled = glIsEnabled(glEnum);
        }
	}
	
	private abstract static class IntState<T extends IntState<T>> extends SubState<T> {
		protected int state;
		
		public IntState(int state) {
			this.state = state;
		}

		@Override
		public boolean isSame(T other) {
			return other.state == this.state;
		}
		
	}
	
	private abstract static class BiIntState<T extends BiIntState<T>> extends IntState<T> {
		protected int state2;
		
		public BiIntState(int state, int state2) {
			super(state);
			this.state2 = state2;
		}

		@Override
		public boolean isSame(T other) {
			return super.isSame(other) && 
					other.state2 == this.state2;
		}
		
	}
	
	private abstract static class TriIntState<T extends TriIntState<T>> extends BiIntState<T> {
		protected int state3;
		
		public TriIntState(int state, int state2, int state3) {
			super(state, state2);
			this.state3 = state3;
		}

		@Override
		public boolean isSame(T other) {
			return super.isSame(other) && 
					other.state3 == this.state3;
		}
		
	}
	
	private abstract static class FloatState<T extends FloatState<T>> extends SubState<T> {
		protected float state;
		
		public FloatState(float state) {
			this.state = state;
		}

		@Override
		public boolean isSame(T other) {
			return other.state == this.state;
		}
		
	}
	
	private abstract static class BiFloatState<T extends BiFloatState<T>> extends FloatState<T> {
		protected float state2;
		
		public BiFloatState(float state, float state2) {
			super(state);
			this.state2 = state2;
		}

		@Override
		public boolean isSame(T other) {
			return super.isSame(other) && other.state2 == this.state2;
		}
		
	}
	
	private abstract static class ColourState<T extends ColourState<T>> extends SubState<T> {
		protected float red, green, blue, alpha;
		
		public ColourState(float red, float green, float blue, float alpha) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.alpha = alpha;
		}

		@Override
		public boolean isSame(T other) {
			return other.red == this.red
					&& other.green == this.green
					&& other.blue == this.blue
					&& other.alpha == this.alpha;
		}
	}
	
	private abstract static class BoxState<T extends BoxState<T>> extends SubState<T> {
		protected int x, y, width, height;
		
		public BoxState(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		@Override
		public boolean isSame(T other) {
			return other.x == this.x
					&& other.y == this.y
					&& other.width == this.width
					&& other.height == this.height;
		}
        
        @Override
        public String toString() {
            return "BoxState: " + x + " " + y + " " + width + " " + height;
        }
	}
	
	public static class ColourBufferState extends State<ColourBufferState> {
		public final BlendState blendState;
		public final BlendFuncState blendFuncState;
		public final BlendColourState blendColourState;
		public final BlendEquationState blendEquationState;
		public final DitherState ditherState;
		public final ClearColourState clearColourState;
		public final ColourWritemaskState colourWritemaskState;
		
		public ColourBufferState(BlendState blendState,
				BlendFuncState blendFuncState,
				BlendColourState blendColourState,
				BlendEquationState blendEquationState, DitherState ditherState,
				ClearColourState clearColourState,
				ColourWritemaskState colourWritemaskState) {
			
			super(new SubState<?>[]{
					blendState,
					blendFuncState,
					blendColourState,
					blendEquationState,
					ditherState,
					clearColourState,
					colourWritemaskState
			});
			this.blendState = blendState;
			this.blendFuncState = blendFuncState;
			this.blendColourState = blendColourState;
			this.blendEquationState = blendEquationState;
			this.ditherState = ditherState;
			this.clearColourState = clearColourState;
			this.colourWritemaskState = colourWritemaskState;
		}
		
		public ColourBufferState() {
			this(
					new BlendState(), 
					new BlendFuncState(), 
					new BlendColourState(), 
					new BlendEquationState(), 
					new DitherState(), 
					new ClearColourState(), 
					new ColourWritemaskState()
					);
		}
	}
	
	public static class BlendState extends EnableState<BlendState> {
		public BlendState(boolean enabled) {
			super(enabled, GL_BLEND);
		}
		
		public BlendState() {
			this(false);
		}
	}
	
	public static class BlendFuncState extends BiIntState<BlendFuncState> {
		public BlendFuncState(int srcFunc, int destFunc) {
			super(srcFunc, destFunc);
		}
		
		public BlendFuncState() {
			this(GL_ONE, GL_ZERO);
		}
		
		@Override
		public void setState() {
			glBlendFunc(state, state2);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_BLEND_SRC_RGB, buffer.asIntBuffer());
            state = buffer.getInt(0);
            glGetIntegerv(GL_BLEND_DST_RGB, buffer.asIntBuffer());
            state2 = buffer.getInt(0);
        }
	}
	
	public static class BlendColourState extends ColourState<BlendColourState> {
		public BlendColourState(float red, float green, float blue,
				float alpha) {
			
			super(red, green, blue, alpha);
		}
		
		public BlendColourState() {
			this(0, 0, 0, 0);
		}
		
		@Override
		public void setState() {
			glBlendColor(red, green, blue, alpha);
		}

        @Override
        public void loadState() {
            glGetFloatv(GL_BLEND_COLOR, buffer.asFloatBuffer());
            red = buffer.getFloat(0);
            green = buffer.getFloat(1);
            blue = buffer.getFloat(2);
            alpha = buffer.getFloat(3);
        }
	}
	
	public static class BlendEquationState extends IntState<BlendEquationState> {
		public BlendEquationState(int equation) {
			super(equation);
		}
		
		public BlendEquationState() {
			this(GL_FUNC_ADD);
		}
		
		@Override
		public void setState() {
			glBlendEquation(state);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_BLEND_EQUATION, buffer.asIntBuffer());
            state = buffer.getInt(0);
        }
	}
	
	public static class DitherState extends EnableState<DitherState> {
		public DitherState(boolean enabled) {
			super(enabled, GL_DITHER);
		}
		
		public DitherState() {
			this(false);
		}
	}
	
	public static class ClearColourState extends ColourState<ClearColourState> {
		public ClearColourState(float red, float green, float blue,
				float alpha) {
			
			super(red, green, blue, alpha);
		}
		
		public ClearColourState() {
			this(0, 0, 0, 0);
		}
		
		@Override
		public void setState() {
			glClearColor(red, green, blue, alpha);
		}

        @Override
        public void loadState() {
            glGetFloatv(GL_COLOR_CLEAR_VALUE, buffer.asFloatBuffer());
            red = buffer.getFloat(0);
            green = buffer.getFloat(1);
            blue = buffer.getFloat(2);
            alpha = buffer.getFloat(3);
        }
	}
	
	public static class ColourWritemaskState extends SubState<ColourWritemaskState> {
		private boolean red, green, blue, alpha;
		
		public ColourWritemaskState(boolean red, boolean green, boolean blue,
				boolean alpha) {
			
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.alpha = alpha;
		}
		
		public ColourWritemaskState() {
			this(true, true, true, true);
		}
		
		@Override
		public void setState() {
			glColorMask(red, green, blue, alpha);
		}

		@Override
		public boolean isSame(ColourWritemaskState other) {
			return other.red == this.red
					&& other.green == this.green
					&& other.blue == this.blue
					&& other.alpha == this.alpha;
		}

        @Override
        public void loadState() {
            glGetBooleanv(GL_COLOR_WRITEMASK, buffer);
            red = buffer.get(0) == GL_TRUE;
            green = buffer.get(1) == GL_TRUE;
            blue = buffer.get(2) == GL_TRUE;
            alpha = buffer.get(3) == GL_TRUE;
        }
	}
	
	public static class DepthBufferState extends State<DepthBufferState> {
		public final DepthTestState depthTestState;
		public final DepthWritemaskState depthWritemaskState;
		public final ClearDepthState clearDepthState;
		public final DepthFuncState depthFuncState;
		
		public DepthBufferState(DepthTestState depthTestState,
				DepthWritemaskState depthWritemaskState,
				ClearDepthState clearDepthState, DepthFuncState depthFuncState) {
			
			super(new SubState<?>[]{
					depthTestState,
					depthWritemaskState,
					clearDepthState,
					depthFuncState
			});
			this.depthTestState = depthTestState;
			this.depthWritemaskState = depthWritemaskState;
			this.clearDepthState = clearDepthState;
			this.depthFuncState = depthFuncState;
		}
		
		public DepthBufferState() {
			this(
					new DepthTestState(), 
					new DepthWritemaskState(), 
					new ClearDepthState(), 
					new DepthFuncState()
					);
		}
	}

	public static class DepthTestState extends EnableState<DepthTestState> {
		public DepthTestState(boolean enabled) {
			super(enabled, GL_DEPTH_TEST);
		}
		
		public DepthTestState() {
			this(false);
		}
	}
	
	public static class DepthWritemaskState extends BooleanState<DepthWritemaskState> {
		public DepthWritemaskState(boolean writemask) {
			super(writemask);
		}
		
		public DepthWritemaskState() {
			this(true);
		}
		
		@Override
		public void setState() {
			glDepthMask(enabled);
		}

        @Override
        public void loadState() {
            glGetBooleanv(GL_DEPTH_WRITEMASK, buffer);
            enabled = buffer.get(0) == GL_TRUE;
        }
	}
	
	public static class ClearDepthState extends FloatState<ClearDepthState> {
		public ClearDepthState(float clearDepth) {
			super(clearDepth);
		}
		
		public ClearDepthState() {
			this(1);
		}
		
		@Override
		public void setState() {
			glClearDepth(state);
		}

        @Override
        public void loadState() {
            glGetFloatv(GL_DEPTH_CLEAR_VALUE, buffer.asFloatBuffer());
            state = buffer.get(0);
        }
	}
	
	public static class DepthFuncState extends IntState<DepthFuncState> {
		public DepthFuncState(int state) {
			super(state);
		}

		public DepthFuncState() {
			this(GL_LESS);
		}

		@Override
		public void setState() {
			glDepthFunc(state);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_DEPTH_FUNC, buffer.asIntBuffer());
            state = buffer.get(0);
        }
	}
	
	public static class PolygonState extends State<PolygonState> {
		public final CullFaceState cullFaceState;
		public final CullFaceModeState cullFaceModeState;
		public final FrontFaceState frontFaceState;
		public final FillOffsetState fillOffsetState;
		public final OffsetState offsetState;
		
		public PolygonState(CullFaceState cullFaceState,
				CullFaceModeState cullFaceModeState,
				FrontFaceState frontFaceState, FillOffsetState fillOffsetState,
				OffsetState offsetState) {
			
			super(new SubState<?>[]{
					cullFaceState,
					cullFaceModeState,
					frontFaceState,
					fillOffsetState,
					offsetState
			});
			this.cullFaceState = cullFaceState;
			this.cullFaceModeState = cullFaceModeState;
			this.frontFaceState = frontFaceState;
			this.fillOffsetState = fillOffsetState;
			this.offsetState = offsetState;
		}
		
		public PolygonState() {
			this(
					new CullFaceState(), 
					new CullFaceModeState(), 
					new FrontFaceState(), 
					new FillOffsetState(), 
					new OffsetState()
					);
		}
	}

	public static class CullFaceState extends EnableState<CullFaceState> {
		public CullFaceState(boolean enabled) {
			super(enabled, GL_CULL_FACE);
		}
		
		public CullFaceState() {
			this(false);
		}
	}
	
	public static class CullFaceModeState extends IntState<CullFaceModeState> {
		public CullFaceModeState(int mode) {
			super(mode);
		}
		
		public CullFaceModeState() {
			this(GL_BACK);
		}

		@Override
		public void setState() {
			glCullFace(state);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_CULL_FACE, buffer.asIntBuffer());
            state = buffer.get(0);
        }
	}
	
	public static class FrontFaceState extends IntState<FrontFaceState> {
		public FrontFaceState(int mode) {
			super(mode);
		}
		
		public FrontFaceState() {
			this(GL_CCW);
		}

		@Override
		public void setState() {
			glFrontFace(state);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_FRONT_FACE, buffer.asIntBuffer());
            state = buffer.get(0);
        }
	}
	
	public static class FillOffsetState extends EnableState<FillOffsetState> {
		public FillOffsetState(boolean enabled) {
			super(enabled, GL_POLYGON_OFFSET_FILL);
		}
		
		public FillOffsetState() {
			this(false);
		}
	}
	
	public static class OffsetState extends BiFloatState<OffsetState> {
		public OffsetState(float factor, float unit) {
			super(factor, unit);
		}
		
		public OffsetState() {
			this(0, 0);
		}

		@Override
		public void setState() {
			glPolygonOffset(state, state2);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_POLYGON_OFFSET_FACTOR, buffer.asIntBuffer());
            state = buffer.get(0);
            glGetIntegerv(GL_POLYGON_OFFSET_UNITS, buffer.asIntBuffer());
            state2 = buffer.get(0);
        }
	}
	
	public static class ScissorState extends State<ScissorState> {
		public final ScissorTestState scissorTestState;
		public final ScissorBoxState scissorBoxState;
		
		public ScissorState(ScissorTestState scissorTestState,
				ScissorBoxState scissorBoxState) {
			
			super(new SubState<?>[]{
					scissorTestState,
					scissorBoxState
			});
			this.scissorTestState = scissorTestState;
			this.scissorBoxState = scissorBoxState;
		}
		
		public ScissorState() {
			this(new ScissorTestState(), new ScissorBoxState());
		}
	}
	
	public static class ScissorTestState extends EnableState<ScissorTestState> {
		public ScissorTestState(boolean enabled) {
			super(enabled, GL_SCISSOR_TEST);
		}
		
		public ScissorTestState() {
			this(false);
		}
	}
	
	public static class ScissorBoxState extends BoxState<ScissorBoxState> {
		public ScissorBoxState(int x, int y, int width, int height) {
			super(x, y, width, height);
		}
		
		public ScissorBoxState() {
			this(0, 0, 0, 0);
		}

		@Override
		public void setState() {
			glScissor(x, y, width, height);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_SCISSOR_BOX, buffer.asIntBuffer());
            x = buffer.get(0);
            y = buffer.get(1);
            width = buffer.get(2);
            height = buffer.get(3);
        }
	}
	
	public static class StencilBufferState extends State<StencilBufferState> {
		public final StencilTestState stenciltestState;
		public final StencilFuncState stencilFuncState;
		public final StencilOpState stencilOpState;
		public final ClearStencilState clearStencilTest;
		public final StencilWritemaskState stencilWritemaskState;
		
		public StencilBufferState(StencilTestState stenciltestState,
				StencilFuncState stencilFuncState,
				StencilOpState stencilOpState,
				ClearStencilState clearStencilTest,
				StencilWritemaskState stencilWritemaskState) {
			
			super(new SubState<?>[]{
					stenciltestState,
					stencilFuncState,
					stencilOpState,
					clearStencilTest,
					stencilWritemaskState
			});
			this.stenciltestState = stenciltestState;
			this.stencilFuncState = stencilFuncState;
			this.stencilOpState = stencilOpState;
			this.clearStencilTest = clearStencilTest;
			this.stencilWritemaskState = stencilWritemaskState;
		}
		
		public StencilBufferState() {
			this(
					new StencilTestState(), 
					new StencilFuncState(), 
					new StencilOpState(), 
					new ClearStencilState(), 
					new StencilWritemaskState()
					);
		}
	}

	public static class StencilTestState extends EnableState<StencilTestState> {
		public StencilTestState(boolean enabled) {
			super(enabled, GL_STENCIL_TEST);
		}
		
		public StencilTestState() {
			this(false);
		}
	}
	
	public static class StencilFuncState extends TriIntState<StencilFuncState> {
		public StencilFuncState(int func, int ref, int mask) {
			super(func, ref, mask);
		}
		
		public StencilFuncState() {
			this(GL_ALWAYS, 0, 0xFFFFFFFF);
		}

		@Override
		public void setState() {
			glStencilFunc(state, state2, state3);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_STENCIL_FUNC, buffer.asIntBuffer());
            state = buffer.get(0);
            glGetIntegerv(GL_STENCIL_REF, buffer.asIntBuffer());
            state2 = buffer.get(0);
            glGetIntegerv(GL_STENCIL_VALUE_MASK, buffer.asIntBuffer());
            state3 = buffer.get(0);
        }
	}
	
	public static class StencilOpState extends TriIntState<StencilOpState> {
		public StencilOpState(int sfail, int dpfail, int dppass) {
			super(sfail, dpfail, dppass);
		}
		
		public StencilOpState() {
			this(GL_KEEP, GL_KEEP, GL_KEEP);
		}

		@Override
		public void setState() {
			glStencilOp(state, state2, state3);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_STENCIL_FAIL, buffer.asIntBuffer());
            state = buffer.get(0);
            glGetIntegerv(GL_STENCIL_PASS_DEPTH_FAIL, buffer.asIntBuffer());
            state2 = buffer.get(0);
            glGetIntegerv(GL_STENCIL_PASS_DEPTH_PASS, buffer.asIntBuffer());
            state3 = buffer.get(0);
        }
	}
	
	public static class ClearStencilState extends IntState<ClearStencilState> {
		public ClearStencilState(int clearStencil) {
			super(clearStencil);
		}
		
		public ClearStencilState() {
			this(0);
		}

		@Override
		public void setState() {
			glClearStencil(state);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_STENCIL_CLEAR_VALUE, buffer.asIntBuffer());
            state = buffer.get(0);
        }
	}
	
	public static class StencilWritemaskState extends IntState<StencilWritemaskState> {
		public StencilWritemaskState(int stencilWritemask) {
			super(stencilWritemask);
		}
		
		public StencilWritemaskState() {
			this(0xFFFFFFFF);
		}

		@Override
		public void setState() {
			glStencilMask(state);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_STENCIL_WRITEMASK, buffer.asIntBuffer());
            state = buffer.get(0);
        }
	}
	
	public static class ViewportState extends State<ViewportState> {
		public final DepthRangeState depthRangeState;
		public final ViewportBoxState viewportBoxState;
		
		public ViewportState(DepthRangeState depthRangeState,
				ViewportBoxState viewportBoxState) {
			
			super(new SubState<?>[]{
					depthRangeState,
					viewportBoxState
			});
			this.depthRangeState = depthRangeState;
			this.viewportBoxState = viewportBoxState;
		}
		
		public ViewportState() {
			this(new DepthRangeState(), new ViewportBoxState());
		}
	}

	public static class DepthRangeState extends BiFloatState<DepthRangeState> {
		public DepthRangeState(float nearDepthRange, float farDepthRange) {
			super(nearDepthRange, farDepthRange);
		}
		
		public DepthRangeState() {
			this(0, 1);
		}
		
		@Override
		public void setState() {
			glDepthRange(state, state2);
		}

        @Override
        public void loadState() {
            glGetFloatv(GL_DEPTH_RANGE, buffer.asFloatBuffer());
            state = buffer.get(0);
            state2 = buffer.get(1);
        }
	}
	
	public static class ViewportBoxState extends BoxState<ViewportBoxState> {
		
		public ViewportBoxState(int x, int y, int width, int height) {
			super(x, y, width, height);
		}
		
		public ViewportBoxState(Viewport viewport) {
			this(viewport.getX(), viewport.getY(), viewport.getWidth(), viewport.getHeight());
		}
		
		public ViewportBoxState() {
			this(0, 0, -1, -1);
		}

		@Override
		public void setState() {
			glViewport(x, y, width, height);
		}

        @Override
        public void loadState() {
            glGetIntegerv(GL_VIEWPORT, buffer.asIntBuffer());
            x = buffer.get(0);
            y = buffer.get(1);
            width = buffer.get(2);
            height = buffer.get(3);
        }
	}
}
