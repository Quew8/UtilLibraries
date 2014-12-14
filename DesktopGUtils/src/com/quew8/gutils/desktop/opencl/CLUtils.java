package com.quew8.gutils.desktop.opencl;

import com.quew8.gutils.BufferUtils;
import com.quew8.gutils.GeneralUtils;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.*;

/**
 * 
 * @author Quew8
 */
public class CLUtils {
    /*private static CLPlatform platform;
    private static List<CLDevice> devices;
    private static CLContext context;
    private static CLCommandQueue queue;
    
    private CLUtils() {
        
    }
    
    public static void init() {
        try {
            CL.create();
            System.err.println(CLPlatform.getPlatforms());
            platform = CLPlatform.getPlatforms().get(0);
            devices = platform.getDevices(CL10.CL_DEVICE_TYPE_GPU);
            context = CLContext.create(platform, devices, null, null, null);
            queue = CL10.clCreateCommandQueue(context, devices.get(0), CL10.CL_QUEUE_PROFILING_ENABLE, null);
        } catch (LWJGLException ex) {
            release();
            System.err.println("An exception occured whilst initialising openCL resources");
            Logger.getLogger(CLUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    public static FloatBuffer readFromMemory(CLMem memory, int size) {
        FloatBuffer writeTo = BufferUtils.createFloatBuffer(size);
        CL10.clEnqueueReadBuffer(queue, memory, CL10.CL_TRUE, 0, writeTo, null, null);
        return writeTo;
    }
    
    public static FloatBuffer readFromMemory(CLMem memory, FloatBuffer writeTo) {
        CL10.clEnqueueReadBuffer(queue, memory, CL10.CL_TRUE, 0, writeTo, null, null);
        return writeTo;
    }
    
    public static void writeToMemory(CLMem memory, FloatBuffer data) {
        CL10.clEnqueueWriteBuffer(queue, memory, CL10.CL_TRUE, 0, data, null, null);
    }
    
    public static void writeToMemory(CLMem memory, float[] data) {
        FloatBuffer dataBuff = BufferUtils.createFloatBuffer(data.length);
        dataBuff.put(data);
        dataBuff.rewind();
        writeToMemory(memory, dataBuff);
    }
    
    public static CLMem createMemory(int type, long size) {
        return CL10.clCreateBuffer(context, type, size, null);
    }
    
    public static CLMem createMemory(int type, FloatBuffer data) {
        return CL10.clCreateBuffer(context, type | CL10.CL_MEM_COPY_HOST_PTR, data, null);
    }
    
    public static CLMem createMemory(int type, float[] data) {
        FloatBuffer dataBuff = BufferUtils.createFloatBuffer(data.length);
        dataBuff.put(data);
        dataBuff.rewind();
        return createMemory(type, dataBuff);
    }
    
    public static void destroyMemory(CLMem mem) {
        CL10.clReleaseMemObject(mem);
    }
    
    public static void waitForQueue() {
        CL10.clFinish(queue);
    }
    
    public static void enqueueNDGlobalSize(CLKernel k, int workItems) {
        PointerBuffer kernel1DGlobalWorkSize = org.lwjgl.BufferUtils.createPointerBuffer(1);
        kernel1DGlobalWorkSize.put(0, workItems);
        CL10.clEnqueueNDRangeKernel(queue, k, 1, null, kernel1DGlobalWorkSize, null, null, null);
    }
    
    public static void enqueueNDGlobalSize(CLKernel k, int workItems, int workGroups) {
        PointerBuffer kernel1DGlobalWorkSize = org.lwjgl.BufferUtils.createPointerBuffer(1);
        kernel1DGlobalWorkSize.put(0, workItems);
        PointerBuffer kernel1DLocalWorkSize = org.lwjgl.BufferUtils.createPointerBuffer(1);
        kernel1DLocalWorkSize.put(0, workGroups);
        CL10.clEnqueueNDRangeKernel(queue, k, 1, null, kernel1DGlobalWorkSize, kernel1DLocalWorkSize, null, null);
    }
    
    public static void release() {
        if(CL.isCreated()) {
            CL10.clReleaseCommandQueue(queue);
            CL10.clReleaseContext(context);
            CL.destroy();
        }
    }
    
    public static String loadText(String fileName) {
        return GeneralUtils.readTextIntoDefaultFormatter(
                GeneralUtils.readFrom(fileName)
                ).getText();
    }
    
    public static class CLUtilsProgram {
        CLProgram program;
        HashMap<String, CLKernel> kernels = new HashMap<>();
        
        public CLUtilsProgram(String name) {
            if(!name.endsWith(".cls")) {
                name += ".cls";
            }
            String vectorAddSource = CLUtils.loadText(name);
            program = CL10.clCreateProgramWithSource(context, vectorAddSource, null);
            int error = CL10.clBuildProgram(program, devices.get(0), "", null);
            Util.checkCLError(error);
        }
        
        public void addKernel(String name) {
            kernels.put(name, CL10.clCreateKernel(program, name, null));
        }
        
        public void setArgument(String kernel, int index, CLMem memory) {
            kernels.get(kernel).setArg(index, memory);
        }
        
        public void setArgument(String kernel, int index, int i) {
            kernels.get(kernel).setArg(index, i);
        }
        
        public void setArgument(String kernel, int index, float f) {
            kernels.get(kernel).setArg(index, f);
        }
        
        public void releaseResources() {
            for (Entry<String, CLKernel> set : kernels.entrySet()) { 
                CL10.clReleaseKernel(set.getValue());
                kernels.remove(set.getKey());
            }
            CL10.clReleaseProgram(program);
        }
    }*/
}
