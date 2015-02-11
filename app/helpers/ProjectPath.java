package helpers;

import play.Play;

public class ProjectPath {
	public static String getProjectPath(){
		if(Play.isProd()){
			return Play.application().configuration().getString("videoUploadPathProd");
		}else{
			return Play.application().path().getPath();
		}
	}
}
