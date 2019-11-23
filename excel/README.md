## 整合springboot

#### 1.添加相关依赖pom.xml

```xml
<dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-base</artifactId>
            <version>3.0.3</version>
        </dependency>
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-web</artifactId>
            <version>3.0.3</version>
        </dependency>
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-annotation</artifactId>
            <version>3.0.3</version>
        </dependency>
```

#### 2.实体类

```java
@Data
public class User implements Serializable {
    @Excel(name = "ID",orderNum = "0")
    private Integer id;

    @Excel(name = "姓名",orderNum = "1")
    private String name;

    @Excel(name = "年龄",orderNum = "2")
    private Integer age;

    @Excel(name = "地址",orderNum = "3")
    private String address;

    @Excel(name = "邮箱",orderNum = "4")
    private String email;

    @Excel(name = "QQ",orderNum = "5")
    private String qq;

    @Excel(name = "电话",orderNum = "6")
    private String phone;

}
```

> @Excel(name = "姓名",orderNum = "1")
>
> 实体类注解
>
> name：字段标题名    orderNum：字段标题顺序



#### 3.工具类

```java
/**
 * @author AlmostLover
 */
public class ExcelUtil {
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, boolean isCreateHeader, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response){
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName,
                                      HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null); downLoadExcel(fileName, response, workbook);
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            //throw new NormalException(e.getMessage());
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows, Class<T> pojoClass){
        if (StringUtils.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        }catch (NoSuchElementException e){
            //throw new NormalException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            //throw new NormalException(e.getMessage());
        } return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){ return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (NoSuchElementException e){
            // throw new NormalException("excel文件不能为空");
        } catch (Exception e) {
            //throw new NormalException(e.getMessage());
            System.out.println(e.getMessage());
        }
        return list;
    }

}
```

### 推荐

> 导出使用：
>
> exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, boolean isCreateHeader, HttpServletResponse response)

| 参数                         | 含义                     |
| ---------------------------- | ------------------------ |
| List<?> list                 | 导出的数据集合           |
| String title                 | 导出文件标题             |
| String sheetName             | 导出文件表名             |
| Class<?> pojoClass           | 对应的实体类             |
| String fileName              | 导出文件名               |
| boolean isCreateHeader       | 是否创建header（字段名） |
| HttpServletResponse response | HttpServletResponse      |

> 导入使用：
>
> <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)

| 参数               | 含义                     |
| ------------------ | ------------------------ |
| MultipartFile file | 前端传入的file文件流     |
| Integer titleRows  | 导入文件的标题所占行数   |
| Integer headerRows | 导入文件的header所占行数 |
| Class<T> pojoClass | 对应的实体类             |

#### 4.controller

```java
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/import")
    public String importShow(){
        return "import";
    }

    @PostMapping("/import")
    @ResponseBody
    public String importExcel(MultipartFile file){
        List<User> users = ExcelUtil.importExcel(file,1,1,User.class);
        return users.toString();
    }

    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response){
        List<User> users = new ArrayList<>();
        for (int i=0;i<=10;i++){
            User user = new User();
            user.setId(i);
            user.setName("name"+i);
            user.setAddress("深圳");
            user.setAge(23+2*i);
            user.setEmail("1212@qq.com");
            user.setPhone("123567");
            user.setQq("554567");
            users.add(user);
        }
        ExcelUtil.exportExcel(users,"用户信息表","表一",User.class,"用户信息表.xls",true,response);
    }
}
```

