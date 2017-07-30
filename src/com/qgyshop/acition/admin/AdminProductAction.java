package com.qgyshop.acition.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Categorysecond;
import com.qgyshop.domain.Orderitem;
import com.qgyshop.domain.Product;

import com.qgyshop.service.CategorySecondService;
import com.qgyshop.service.ProductService;
import com.qgyshop.util.PageUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by vivid on 2017/3/16.
 * 商品
 */
@Controller
@Scope("prototype")
public class AdminProductAction  extends ActionSupport implements ModelDriven<Product>{
    Product model=new Product();
    @Override
    public Product getModel() {
        return model;
    }

    //分页
    int page;

    //事物 商品
    @Resource
    private ProductService productService;
    //二级分类
    @Resource
    private CategorySecondService categorySecondService;

    //日志
    Log log= LogFactory.getLog(this.getClass());
     //与上传图片有关的三个参数
    private File upload; //文件流 名字随便取 但千万别和模型里面的属性重名 模型的优先级高 我觉得upload挺好
    private String uploadFileName; //文件名 命名规则 file 的变量名 + FileName
    private String uploadContentType; //文件类型 命名规则 file 的变量名 + ContentType

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    /**
     * 展示商品带分页
     */
        public String findAll(){
            log.info("当前接受到的page====================="+page);

        PageUtil<Product> pageUtil=productService.findAll(page);
        //放到栈里
        ActionContext.getContext().getValueStack().set("pageUtil",pageUtil);

        return "findAll";
    }
    /**
     * 保存商品信息
     * @return
     */
    public String save(){

        saveimg();
        //调用服务。。保存商品
        productService.save(model);
        return "toFindAll";
    }

    /**
     * 删除
     * @return
     */
    public String delete(){
        //商品是关联着订单的 我感觉 后台基本数据不能被删除
        Product product=productService.findById(model.getPid());
        productService.delete(product);
        return "toFindAll";
    }

    /**
     * 去添加界面
     * @return
     */
    public String addPage(){
        //获取所有的二级分类信息
        Collection<Categorysecond> csList=categorySecondService.findAll();
        //放入栈中
        ActionContext.getContext().getValueStack().set("csList",csList);
        return "toAdd";
    }
    /**
     * 去修改界面
     * @return
     */
    public String edit(){
        //获取所有的二级分类信息
        Collection<Categorysecond> csList=categorySecondService.findAll();
        //放入栈中
        ActionContext.getContext().getValueStack().set("csList",csList);
//        先查询
        Product product=productService.findById(model.getPid());
        //商品对应的二级分类的懒加载已经关了 可以直接获取到自身对应的二级分类信息
        model=product; //使用model像页面传数据

        return "toEdit";
    }

    public String update(){
        //保存图片
        saveimg();

        productService.update(model);
        return "toFindAll";
    }

    private void saveimg(){
        //设置时间
        model.setPdate(new Date());
        //新传的图片不为空
        if (upload!=null){

            //删除原来的图片 在新图片不为空 且之前有图片 的时候执行 否则不执行 第一次的话也不会执行到这里因为原来没有图片
            if (model.getImage()!=null){
                String oldPath=ServletActionContext.getServletContext().getRealPath(model.getImage());
                File file=new File(oldPath);
                if (file.exists()){
                    file.delete();
                }
            }

            System.out.println("uploadFileName++++++++++++++"+uploadFileName);
            System.out.println("uploadContentType++++++++++++++"+uploadContentType);
            //将图片上传到服务器
            //获得图片的服务器断路径
            String path= ServletActionContext.getServletContext().getRealPath("/products/2");


            //创建文件类型对象 ：
            File diskFile=new File(path+"//" +uploadFileName);

            //文件上传
            try {
                FileUtils.copyFile(upload,diskFile);

                //设置图片名和相对路径
                model.setImage("products/2/"+uploadFileName);
                this.addActionMessage("图片保存成功");
            } catch (IOException e) {
                e.printStackTrace();
                this.addActionMessage("图片保存失败");
            }
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
