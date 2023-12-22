package com.nhnacademy.shoppingmall.common.page.Service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import com.nhnacademy.shoppingmall.common.page.Service.PageService;
import com.nhnacademy.shoppingmall.common.page.repository.PageReopsitory;
import com.nhnacademy.shoppingmall.common.page.repository.impl.PageRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction.MockInitializer;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageServiceImplTest {

    PageReopsitory pageRepository = Mockito.mock(PageReopsitory.class);
    PageService pageService = new PageServiceImpl(pageRepository);

    @Test
    void productToTalPage() {
        Mockito.when(pageRepository.productTotalPage(anyInt())).thenReturn(1);
        pageService.productToTalPage(anyInt());
        Mockito.verify(pageRepository, Mockito.times(1)).productTotalPage(anyInt());
    }

    @Test
    void getProductContents() {
        Mockito.when(pageRepository.getProductContents(anyInt(),anyInt())).thenReturn(null);
        pageService.getProductContents(anyInt(), anyInt());
        Mockito.verify(pageRepository, Mockito.times(1)).getProductContents(anyInt(), anyInt());
    }
}