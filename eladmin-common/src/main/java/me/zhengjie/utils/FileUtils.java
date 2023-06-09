package me.zhengjie.utils;


import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import static java.lang.System.in;

/**
 * 文件处理辅助类
 *
 * @author yjmyzz@126.com
 * @version 0.2
 * @since 2014-11-17
 *
 */
public class FileUtils {
    private static final String TAG = "FileUtils";
    /**
     * 当前目录路径
     */
    public static String currentWorkDir = System.getProperty("user.dir") + "\\";

    /**
     * 左填充
     *
     * @param str
     * @param length
     * @param ch
     * @return
     */
    public static String leftPad(String str, int length, char ch) {
        if (str.length() >= length) {
            return str;
        }
        char[] chs = new char[length];
        Arrays.fill(chs, ch);
        char[] src = str.toCharArray();
        System.arraycopy(src, 0, chs, length - src.length, src.length);
        return new String(chs);

    }

    /**
     * 删除文件
     *
     * @param fileName
     *            待删除的完整文件名
     * @return
     */
    public static boolean delete(String fileName) {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            result = f.delete();

        } else {
            result = true;
        }
        return result;
    }

    /***
     * 递归获取指定目录下的所有的文件（不包括文件夹）
     *
     *
     */
    public static ArrayList<File> getAllFiles(String dirPath) {
        File dir = new File(dirPath);

        ArrayList<File> files = new ArrayList<File>();

        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File f = fileArr[i];
                if (f.isFile()) {
                    files.add(f);
                } else {
                    files.addAll(getAllFiles(f.getPath()));
                }
            }
        }
        return files;
    }

    /**
     * 获取指定目录下的所有文件(不包括子文件夹)
     *
     * @param dirPath
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles();
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }


    public static ArrayList<File> getDirFilesForPrefix(String dirPath,final String preffix){
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerPreffix = preffix.toLowerCase();
                if (lowerName.startsWith(lowerPreffix)) {
                    return true;
                }
                return false;
            }

        });
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }
    /**
     * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
     *
     * @param dirPath
     *            目录路径
     * @param suffix
     *            文件后缀
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath,
                                              final String suffix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerSuffix = suffix.toLowerCase();
                if (lowerName.endsWith(lowerSuffix)) {
                    return true;
                }
                return false;
            }

        });
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 读取文件内容
     *
     * @param fileName
     *            待读取的完整文件名
     * @return 文件内容
     * @throws IOException
     */
    public static String read(String fileName) throws IOException {
        File f = new File(fileName);
        FileInputStream fs = new FileInputStream(f);
        String result = null;
        byte[] b = new byte[fs.available()];
        fs.read(b);
        fs.close();
        result = new String(b);
        return result;
    }

    /**
     * 写文件
     *
     * @param fileName
     *            目标文件名
     * @param fileContent
     *            写入的内容
     * @return
     * @throws IOException
     */
    public static boolean write(String fileName, String fileContent)
            throws IOException {
        boolean result = false;
        File f = new File(fileName);
        FileOutputStream fs = new FileOutputStream(f);
        byte[] b = fileContent.getBytes();
        fs.write(b);
        fs.flush();
        fs.close();
        result = true;
        return result;
    }

    /**
     * 追加内容到指定文件
     *
     * @param fileName
     * @param fileContent
     * @return
     * @throws IOException
     */
    public static boolean append(String fileName, String fileContent)
            throws IOException {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            RandomAccessFile rFile = new RandomAccessFile(f, "rw");
            byte[] b = fileContent.getBytes();
            long originLen = f.length();
            rFile.setLength(originLen + b.length);
            rFile.seek(originLen);
            rFile.write(b);
            rFile.close();
        }
        result = true;
        return result;
    }

    public boolean doFile(File file) {
        String[] strs = file.getName().split("\\.");
        String filePath = file.getParent();
        File thisfiledir = new File(filePath);
        String filep = thisfiledir.getParent();
        if (Objects.equals(Integer.valueOf(strs[strs.length - 2]),thisfiledir.listFiles().length)){
            ArrayList<File> partFiles = FileUtils.getDirFilesForPrefix(filePath,
                    strs[0] + "." + strs[1] + ".");
            Collections.sort(partFiles, new FileComparator());
            try {
                mergePartFiles(filePath,
                        strs[0] + "." + strs[1] + ".",
                        partFiles.get(0).length(),
                        filep+"\\" + strs[0] + "." + strs[1]
                );
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 拆分文件
     *
     * @param byteSize
     *            按多少字节大小拆分
     * @return 拆分后的文件名列表
     * @throws IOException
     */
    public List<String> splitBySize(File file, int byteSize)
            throws IOException {
        List<String> parts = new ArrayList<String>();
        int count = (int) Math.ceil(file.length() / (double) byteSize);
        int sumcount = count;
        int countLen = (count + "").length();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count,
                count * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(count * 2));

        for (int i = 0; i < count; i++) {
            String partFileName = file.getName() + "."
                    + leftPad((i + 1) + "", countLen, '0') + "."+ sumcount+".part";
            threadPool.execute(new SplitRunnable(byteSize, i * byteSize,
                    partFileName, file));
            parts.add(file.getParent() +"/"+partFileName);
        }
        while (threadPool.getActiveCount() != 0){
            try {
                Thread.sleep(50);
                while (threadPool.getActiveCount() != 0)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return parts;
    }

    public Boolean deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            return false;
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteFile(f);
            } else {
                f.delete();
            }
        }
        file.delete();
        return true;
    }

    /**
     * 合并文件
     *
     * @param dirPath
     *            拆分文件所在目录名
     * @param partFileSize
     *            拆分文件的字节数大小
     * @param mergeFileName
     *            合并后的文件名
     * @throws IOException
     */
    public void mergePartFiles(String dirPath, String partFilePreffix,
                               long partFileSize, String mergeFileName) throws IOException {
        ArrayList<File> partFiles = FileUtils.getDirFilesForPrefix(dirPath,
                partFilePreffix);
        Collections.sort(partFiles, new FileComparator());
        RandomAccessFile randomAccessFile = new RandomAccessFile(mergeFileName,
                "rw");
        randomAccessFile.setLength(partFileSize * (partFiles.size() - 1)
                + partFiles.get(partFiles.size() - 1).length());
        randomAccessFile.close();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                partFiles.size(), partFiles.size() * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(partFiles.size() * 2));

        for (int i = 0; i < partFiles.size(); i++) {
            threadPool.execute(new MergeRunnable(i * partFileSize,
                    mergeFileName, partFiles.get(i)));
        }
        while (threadPool.getActiveCount() != 0){
            try {
                Thread.sleep(50);
                while (threadPool.getActiveCount() != 0)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        deleteFile(new File(dirPath));
    }

    /**
     * 根据文件名，比较文件
     *
     * @author yjmyzz@126.com
     *
     */
    public static class FileComparator implements Comparator<File> {
        public int compare(File o1, File o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }
    public static byte[] readFileToByteArray(File file) {
//        File file = new File(path);
        if(!file.exists()) {
//            Log.e(TAG,"File doesn't exist!");
            return null;
        }
        try {
            FileInputStream ind = new FileInputStream(file);
            long inSize = ind.getChannel().size();//判断FileInputStream中是否有内容
            if (inSize == 0) {
//                Log.d(TAG,"The FileInputStream has no content!");
                return null;
            }

            byte[] buffer = new byte[ind.available()];//in.available() 表示要读取的文件中的数据长度
            ind.read(buffer);  //将文件中的数据读到buffer中
            return buffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 分割处理Runnable
     *
     * @author yjmyzz@126.com
     *
     */
    private class SplitRunnable implements Runnable {
        int byteSize;
        String partFileName;
        File originFile;
        int startPos;

        public SplitRunnable(int byteSize, int startPos, String partFileName,
                             File originFile) {
            this.startPos = startPos;
            this.byteSize = byteSize;
            this.partFileName = partFileName;
            this.originFile = originFile;
        }

        public void run() {
            RandomAccessFile rFile;
            OutputStream os;
            try {
                rFile = new RandomAccessFile(originFile, "r");
                partFileName = originFile.getParent() +"/"+partFileName;
                byte[] b = new byte[byteSize];
                rFile.seek(startPos);// 移动指针到每“段”开头
                int s = rFile.read(b);
                os = new FileOutputStream(partFileName);
                os.write(b, 0, s);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并处理Runnable
     *
     * @author yjmyzz@126.com
     *
     */
    private class MergeRunnable implements Runnable {
        long startPos;
        String mergeFileName;
        File partFile;

        public MergeRunnable(long startPos, String mergeFileName, File partFile) {
            this.startPos = startPos;
            this.mergeFileName = mergeFileName;
            this.partFile = partFile;
        }

        public void run() {
            RandomAccessFile rFile;
            try {
                rFile = new RandomAccessFile(mergeFileName, "rw");
                rFile.seek(startPos);
                FileInputStream fs = new FileInputStream(partFile);
                byte[] b = new byte[fs.available()];
                fs.read(b);
                fs.close();
                rFile.write(b);
                rFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean mkNewDir(File file){
        if (file.exists() && file.isDirectory()){
            return true;
        }else {
            boolean mkdir = file.mkdir();
            return mkdir;
        }
    }

    public static int countFiles(File file){
        return file.listFiles().length;
    }
}
