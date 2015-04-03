
public class Metro_CallKnitro {
	
	public static void runFullLP (String modName, String datName, String runName) {
		Metro_FullLPModFile.generateFullLPMod(modName);
		Metro_FullLPDatFile.generateFullLPDatFile(datName);
		Metro_RunSolver.run(runName);
	}
}
