/*****************************************************************************
 *                        Shapeways, Inc Copyright (c) 2015
 *                               Java Source
 *
 * This source is licensed under the GNU LGPL v2.1
 * Please read http://www.gnu.org/copyleft/lgpl.html for more information
 *
 * This software comes with the standard NO WARRANTY disclaimer for any
 * purpose. Use it at your own risk. If there's a problem you get to fix it.
 *
 ****************************************************************************/
package opencl;

import abfab3d.param.Parameterizable;
import abfab3d.param.DoubleParameter;
import abfab3d.param.Vector3dParameter;
import abfab3d.param.IntParameter;

import abfab3d.datasources.ImageMap;

import javax.vecmath.Vector3d;

import static opencl.CLUtils.floatToInt;

import static abfab3d.util.Units.MM;
import static abfab3d.util.Output.printf;

/**
   CL code generatror for ImageMap
   @author Vladimir Bulatov 
 */
public class CLImageMap  extends CLNodeBase {

    static final boolean DEBUG = true;
    static int OPCODE = Opcodes.oGRID2DBYTE;
    /*
typedef struct {
    int size;  // size of struct in words 
    int opcode; // opcode 
    // custom parameters
    // coefficients to calculate data value
    float vOffset; // value = byteValue*vFactor + vOffset;
    float vFactor; 

    float rounding; // edges rounding      
    int repeat; // (repatX | repeatY <<1)
    int nx; // grid count in x and y directions 
    int ny; // grid count in x and y directions 

    float3 center;  // center in world units

    float3 halfsize; // size in world units

    float3 origin; // location of bottom left corner
    float vscale; // 1/voxelSize 
    float outsideValue; 
    int data; // location of data in the data buffer 
    PTRDATA char *pData; // actual grid data 
} sGrid2dByte;
    */

    static int STRUCTSIZE = 24;
    
    int buffer[] = new int[STRUCTSIZE];
    
    public int getCLCode(Parameterizable node, CLCodeBuffer codeBuffer) {

        int wcount =  super.getTransformCLCode(node,codeBuffer);

        ImageMap image = (ImageMap)node;
        
        Vector3d center = ((Vector3dParameter)node.getParam("center")).getValue();
        Vector3d size = ((Vector3dParameter)node.getParam("size")).getValue();
        double v0 = (Double)node.getParam("blackDisplacement").getValue();
        double v1 = (Double)node.getParam("whiteDisplacement").getValue();
        
        double rounding = 0;
        int repeat = 0;
        
        if(DEBUG)printf("imageMap([%7.5f,%7.5f,%7.5f][%7.5f,%7.5f,%7.5f])\n", center.x, center.y, center.z, size.x, size.y, size.z);
        int nx = image.getBitmapWidth();
        int ny = image.getBitmapHeight();
        byte[] data = new byte[nx*ny];
        double outsideValue = 0.; 
        image.getBitmapDataUByte(data);

        double vOffset = v0;
        double vFactor = (v1-v0)/255.;

        int offset = codeBuffer.addData(data);

        int c = 0;
        buffer[c++] = STRUCTSIZE;
        buffer[c++] = OPCODE;
        buffer[c++] = floatToInt(vOffset); // vOffset 
        buffer[c++] = floatToInt(vFactor); // vFactor
        buffer[c++] = floatToInt(rounding); // rounding
        buffer[c++] = repeat;
        buffer[c++] = nx; // dim[0]
        buffer[c++] = ny; // dim[1]I remember
        buffer[c++] = floatToInt(center.x);
        buffer[c++] = floatToInt(center.y);
        buffer[c++] = floatToInt(center.z);
        buffer[c++] = 0; // alignment
        buffer[c++] = floatToInt(size.x/2.);
        buffer[c++] = floatToInt(size.y/2.);
        buffer[c++] = floatToInt(size.z/2.);
        buffer[c++] = 0;// alignment
        buffer[c++] = floatToInt(center.x-size.x/2.);
        buffer[c++] = floatToInt(center.y-size.y/2.);
        buffer[c++] = floatToInt(center.z-size.z/2.);
        buffer[c++] = 0;// alignment
        buffer[c++] = floatToInt(nx/size.x); //1./pixelSize 
        buffer[c++] = floatToInt(ny/size.y); //1./pixelSize 
        buffer[c++] = floatToInt(outsideValue); 
        buffer[c++] = offset; // data offset 

        codeBuffer.add(buffer, STRUCTSIZE);
        wcount += STRUCTSIZE;

        wcount +=  super.getMaterialCLCode(node,codeBuffer);
       
        return wcount;
               
    }
}