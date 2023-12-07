package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = Method.POST, value = "/admin/productUpdate.do")
@Slf4j
public class ProductUpdatePostController implements BaseController {
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String fileName = null;
            Part imagePart = req.getPart("productImage");
            String contentDisposition = imagePart.getHeader(CONTENT_DISPOSITION);
            log.debug(String.valueOf(imagePart.getSize()));
            if (imagePart.getSize() > 0 && contentDisposition.contains("filename=")) {
                fileName = extractFileName(contentDisposition);

                if (imagePart.getSize() > 0) {
                    imagePart.write(getAbsolutePath(req, fileName));
                }
            }
            int productId = Integer.parseInt(req.getParameter("productId"));
            Products products = null;
            if(productsRepository.findByProductId(productId).isPresent()){
                products = productsRepository.findByProductId(productId).get();
            }else{
                throw new RuntimeException("상품이 존재하지 않습니다.");
            }
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String modelNumber = req.getParameter("modelNumber");
            String modelName = req.getParameter("modelName");
            int unitCost = Integer.parseInt(req.getParameter("unitCost"));
            String description = req.getParameter("description");
            products.setCategoryId(categoryId);
            products.setModelName(modelName);
            products.setModelNumber(modelNumber);
            products.setUnitCost(unitCost);
            products.setProductImage( Objects.isNull(fileName) ? products.getProductImage() : fileName);
            products.setDescription(description);
            productsRepository.update(products);
            return "redirect:/admin/productList.do";
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private String extractFileName(String contentDisposition) {
        log.error("contentDisposition:{}", contentDisposition);
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }

    private String getAbsolutePath(HttpServletRequest req, String fileName) {
        return req.getServletContext().getRealPath(ViewResolver.IMAGE_DIR) + fileName;
    }
}
