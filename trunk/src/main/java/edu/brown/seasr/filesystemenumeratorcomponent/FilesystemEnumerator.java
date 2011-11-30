package edu.brown.seasr.filesystemenumeratorcomponent;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * User: mdellabitta
 * Date: 2011-03-23
 * Time: 8:45 AM
 */
public class FilesystemEnumerator {

    public String[] getURIs(String parentDirString, String regexp, boolean recurse) throws Exception {
        
        FilenameFilter filter = getFilter(regexp);
        File parentDir = new File(parentDirString);

        if (!(parentDir.exists() && parentDir.isDirectory() && parentDir.canRead())) {
            throw new RuntimeException(parentDirString + " is not a readable directory.");
        }
        
        ArrayList<String> results = new ArrayList<String>();

        if (recurse) {
            deep(parentDir, filter, results);
            
        } else {
            shallow(parentDir, filter, results);
        }

        return results.toArray(new String[results.size()]);
    }

    private FilenameFilter getFilter(String regexp) throws Exception {

        if (regexp == null) {
            return new PassthroughFileNameFilter();
            
        } else {
            return new RegexpFilenameFilter(regexp);
        }
    }

    private void deep(File dir, FilenameFilter filter, ArrayList<String> results) {

        String[] names = dir.list(filter);
        results.ensureCapacity(results.size() + names.length);

        for (String name: names) {
            File file = new File(dir, name);

            if (file.isFile()) {
                results.add(file.toURI().toString());
            }
        }

        File[] subDirs = dir.listFiles(new DirectoryFilter());

        for (File subDir: subDirs) {
            deep(subDir, filter, results);
        }
    }

    private void shallow(File dir, FilenameFilter filter, ArrayList<String> results) {
 
        String[] names = dir.list(filter);
        results.ensureCapacity(names.length);

        for (String name : names) {
            File file = new File(dir, name);

            if (file.isFile()) {
                results.add(file.toURI().toString());
            }
        }
    }

    public class RegexpFilenameFilter implements FilenameFilter {

        private Pattern p;

        public RegexpFilenameFilter(String patternString) {
            p = Pattern.compile(patternString);
        }

        public boolean accept(File file, String filename) {
            return p.matcher(filename).find();
        }
    }

    public class PassthroughFileNameFilter implements FilenameFilter {
        public boolean accept(File file, String s) {
            return true;
        }
    }

    public class DirectoryFilter implements FileFilter {
        public boolean accept(File file) {
            return file.isDirectory();
        }
    }
}
