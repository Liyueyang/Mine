//======>>>>>>摘自郭霖CSDN博客：http://blog.csdn.net/guolin_blog/article/details/8689140======>>>>>>
/**
 * 计算已使用内存的百分比，并返回。
 * 
 * @param context
 *            可传入应用程序上下文。
 * @return 已使用内存的百分比，以字符串形式返回。
 */
public static String getUsedPercentValue(Context context) {
  String dir = "/proc/meminfo";
  try {
    FileReader fr = new FileReader(dir);
    BufferedReader br = new BufferedReader(fr, 2048);
    String memoryLine = br.readLine();
    String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
    br.close();
    long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
    long availableSize = getAvailableMemory(context) / 1024;
    int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
    return percent + "%";
  } catch (IOException e) {
    e.printStackTrace();
  }
  return "悬浮窗";
}

/**
 * 获取当前可用内存，返回数据以字节为单位。
 * 
 * @param context
 *            可传入应用程序上下文。
 * @return 当前可用内存。
 */
private static long getAvailableMemory(Context context) {
  ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
  getActivityManager(context).getMemoryInfo(mi);
  return mi.availMem;
}
//<<<<<<======摘自郭霖CSDN博客：http://blog.csdn.net/guolin_blog/article/details/8689140<<<<<<======