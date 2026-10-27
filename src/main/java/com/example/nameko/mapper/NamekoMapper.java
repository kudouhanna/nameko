
package com.example.nameko.mapper;

import com.example.nameko.dto.Nameko;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NamekoMapper {

    @Select("""
            SELECT id, name, rarity, description, image, created_at
            FROM namekos
            ORDER BY id ASC
            """)
    List<Nameko> findAll();


    @Insert("""
            INSERT INTO namekos (name, rarity, description, image)
            VALUES (#{name}, #{rarity}, #{description}, #{image})
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    int insert(Nameko nameko);


    @Select("""
            SELECT id, name, rarity, description, image, created_at
            FROM namekos
            WHERE id = #{id}
            """)
    Nameko findById(long id);


    @Delete("DELETE FROM namekos WHERE id = #{id}")
    int delete(long id);


    @Update("""
            UPDATE namekos
            SET name = #{name},
                rarity = #{rarity},
                description = #{description},
                image = #{image}
            WHERE id = #{id}
            """)
    int update(Nameko nameko);


}