package de.mannheim.wifo2.fesas;

import java.io.File;
import java.io.IOException;

import de.mannheim.wifo2.fesas.mwImplBase.FesasCentralInstance;
import de.mannheim.wifo2.fesas.ui.FesasGUI;
import jodd.util.ClassLoaderUtil;

public class FesasWrapper {

	public static void main(String args[]) {
		
		File repositoryFolder = getRepositoryFolder();
		emptyRepositoryFolder(repositoryFolder);
		initClassLoader(repositoryFolder);
		
		args = getProps();
		
		//create a local instance of the object and export the service
		if (FesasWrapperProperties.USE_GUI == true) {
			FesasGUI insertLogicConsole = new FesasGUI(FesasWrapperProperties.SCENARIO_NAME);
			insertLogicConsole.setVisible(true);
			insertLogicConsole.startUI();
		}
		
		System.out.println("Load FESAS Wrapper");
		String classpath = System.getProperty("java.class.path");
		System.out.println("Class path : " + classpath);
		
		FesasCentralInstance.main(args);
	}


	private static boolean emptyRepositoryFolder(File repositoryFolder) {

		if (repositoryFolder.exists()) {
			repositoryFolder.delete();
		}
		boolean result = repositoryFolder.mkdir();
		return result;
	}

	private static void initClassLoader(File moduleFolder) {
		
		ClassLoaderUtil.addFileToClassPath(moduleFolder,ClassLoader.getSystemClassLoader());

	}

	private static String[] getProps() {
		
		FesasWrapperProperties.getInstance().initializeProps();
		
		String[] args = new String[8];
		args[0] = FesasWrapperProperties.DEVICE_ID;
		args[1] = FesasWrapperProperties.AL_CONFIG_FILE;
		args[2] = FesasWrapperProperties.COMMUNICATION_CONFIG_FILE;
		args[3] = FesasWrapperProperties.SCENARIO_NAME;
		args[4] = FesasWrapperProperties.DELAY;
		args[5] = FesasWrapperProperties.USE_ALM;
		args[6] = FesasWrapperProperties.USE_GUI + "";
		args[7] = FesasWrapperProperties.ONE_THREADED + "";
		
		return args;
	}

	private static File getRepositoryFolder() {
		File current = new File(".");
		File repositoryFolder;
		try {
			repositoryFolder = new File(current.getCanonicalPath() + File.separator + "FESASRepository");
			return repositoryFolder;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
