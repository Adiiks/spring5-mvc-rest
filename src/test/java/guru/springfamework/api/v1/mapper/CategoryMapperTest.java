package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryMapperTest {

    public static final long ID = 1L;
    public static final String CATEGORY = "category";

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {

        Category category = new Category();
        category.setId(ID);
        category.setName(CATEGORY);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertNotNull(categoryDTO);
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(CATEGORY, categoryDTO.getName());
    }
}
