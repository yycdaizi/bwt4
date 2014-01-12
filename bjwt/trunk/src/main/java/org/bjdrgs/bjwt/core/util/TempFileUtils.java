package org.bjdrgs.bjwt.core.util;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import org.apache.commons.io.FileUtils;

public class TempFileUtils {

	public static File createTempFile() throws IOException{
		return createTempFile(null, null, null, true);
	}
	
	public static File createTempFile(String prefix, String suffix) throws IOException{
		return createTempFile(prefix, suffix, null, true);
	}
	
	public static File createTempFile(String prefix, String suffix,
            File directory, boolean deleteOnExit) throws IOException {
		if (prefix == null)
            prefix = "jfile_";
        if (suffix == null)
            suffix = ".tmp";
        
		File file = File.createTempFile(prefix, suffix, directory);
		if(deleteOnExit){
        	file.deleteOnExit();
        }
        return file;
	}
	
	public static File createTempDir() throws IOException{
		return createTempDir(null, null, null, true);
	}

	public static File createTempDir(String prefix, String suffix,
            File directory, boolean deleteOnExit) throws IOException {
		if (prefix == null)
            prefix = "jdir_";
        if (suffix == null)
            suffix = ".tmp";

        File tmpdir = (directory != null) ? directory
                                          : FileUtils.getTempDirectory();
        File file;
        try {
            do {
                file = generateFile(prefix, suffix, tmpdir);
            } while (file.exists());
            if (!file.mkdirs())
                throw new IOException("Unable to create temporary file");
        } catch (SecurityException se) {
            // don't reveal temporary directory location
            if (directory == null)
                throw new SecurityException("Unable to create temporary file");
            throw se;
        }
        if(deleteOnExit){
        	file.deleteOnExit();
        }
        return file;
	}
	
	private static final SecureRandom random = new SecureRandom();
    private static File generateFile(String prefix, String suffix, File dir)
        throws IOException
    {
        long n = random.nextLong();
        if (n == Long.MIN_VALUE) {
            n = 0;      // corner case
        } else {
            n = Math.abs(n);
        }
        String name = prefix + Long.toString(n) + suffix;
        File f = new File(dir, name);
        if (!name.equals(f.getName()))
            throw new IOException("Unable to create temporary file");
        return f;
    }
}
