package multiProcessing;
import utils.DumpData;

import java.io.File;
import java.io.IOException;

/**
 * Runnable class to dummp the data
 * 
 * @author axel poulet
 *
 */
public class RunnableDumpData extends Thread implements Runnable{

	/**String: path where save the dump data  */
	private String _outdir ="";
	/**String: name of the chr*/
	private String _chrName = "";
	/**int: chr size */
	private int _chrSize = 0;
	/** DumpData object run juicertoolbox.jar*/
	private DumpData _dumpData;
	/**int: bin resolution*/
	private int _res = 0;
	/**int: image Size */
	private int _matrixSize = 0;
	/**int: size of the step to run a chr */
	private int _step = 0;
	
	/**
	 * Constructor
	 * 
	 * @param outdir
	 * @param chrName
	 * @param chrSize
	 * @param dumpData
	 * @param res
	 * @param matrixSize
	 * @param step
	 */
	
	public RunnableDumpData (String outdir, String chrName, int chrSize, DumpData dumpData,int res, int matrixSize, int step){
		this._outdir = outdir;
		this._chrName = chrName;
		this._chrSize = chrSize;
		this._res = res;
		this._matrixSize = matrixSize;
		this._step = step;
		this._dumpData = dumpData;
	}
	
	/**
	 * create the name file, and call the dumpData function
	 * 
	 */
	public void run(){
		ProcessDumpData._nbLance++;
		ProcessDumpData._continuer = true;
		boolean juicerTools;
		String expected ="";
		String outdir = this._outdir+File.separator+this._chrName+File.separator;
		File file = new File(outdir);
		if (file.exists()==false) file.mkdir();
		int step = this._step*this._res;
		int j = this._matrixSize*this._res;
		if (j > _chrSize) {j = _chrSize; }
		String test = this._chrName+":0:"+j;
		String name = outdir+this._chrName+"_0_"+j+".txt";
		this._dumpData.getExpected(test,name);
		String normOutput = this._outdir+File.separator+"normVector";
		file = new File(normOutput);
		if (file.exists()==false){file.mkdir();}
		try {
			this._dumpData.getNormVector(this._chrName,normOutput+File.separator+this._chrName+".norm");
			System.out.println("start dump "+this._chrName+" size "+this._chrSize);
			if(j > this._chrSize) j = this._chrSize;
			for(int i = 0 ; j-1 <= this._chrSize; i+=step,j+=step){
				int end =j-1;
				String dump = this._chrName+":"+i+":"+end;
				name = outdir+this._chrName+"_"+i+"_"+end+".txt";
				System.out.println("start dump "+this._chrName+" size "+this._chrSize+" dump "+dump);
				System.out.println(expected);
				juicerTools = this._dumpData.dumpObservedMExpected(dump,name);
				if (juicerTools == false){
					System.out.print(dump+" "+"\n"+juicerTools+"\n");
					System.exit(0);
				}
				
				if(j+step > this._chrSize && j < this._chrSize){
					j= this._chrSize;
					i+=step;
					dump = this._chrName+":"+i+":"+j;
					name = outdir+this._chrName+"_"+i+"_"+j+".txt";
					System.out.println("start dump "+this._chrName+" size "+this._chrSize+" dump "+dump);
					juicerTools = this._dumpData.dumpObservedMExpected(dump,name);
					if (juicerTools == false){
						System.out.print(dump+" "+"\n"+juicerTools+"\n");
						System.exit(0);
					}
				}
			}
			System.out.println("##### End dump "+this._chrName);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		System.gc();
		ProcessDumpData._nbLance--;
	}
}