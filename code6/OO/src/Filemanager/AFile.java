package Filemanager;

import java.io.File;

public class AFile {
	File file;
	File parent;
	String path;
	String filename;
	boolean isfile;
	long ModTime;
	long size;
	public AFile(File file){
		this.file=file;
		this.parent=file.getParentFile();
		this.path=new String(file.getAbsolutePath());
		this.filename=new String(file.getName());
		this.ModTime=file.lastModified();
		this.size=GetSize(file);
		this.isfile=file.isFile();
		
	}
	
	
	public long GetSize(File file){
		long size=0;
		int i=0;
		if(file.isFile()){
			return file.length();
		}
		else{
			File list[]=file.listFiles();
			if(list==null){
				System.out.println(file.getName());
				return size;
			}
			for(i=0;i<list.length;i++){
				if(list[i].isFile()){
					size+=list[i].length();
				}
			}
			return size;
		}
	}
}
