package me.jcala.xmarket.server.entity.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "trade")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Trade {
    @Id
    private String id;//商品ID
    private String title;//商品名字
    private User author;//商品所属者
    private long price;
    private String desc;
    private String tagName;
    private String schoolName;//所属的学校名称
    private List<String> imgUrls;//商品图片
    private long createTime;//商品创建时间
    private int status;//商品状态。0:在售，1:售出,2:捐赠
}
