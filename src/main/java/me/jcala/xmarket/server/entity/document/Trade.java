package me.jcala.xmarket.server.entity.document;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;


@Document(collection = "trade")
@Getter
@Setter
public class Trade {
    @Id
    private String id;//商品ID
    @NotEmpty
    private String title;//商品名字
    @NotNull
    private User author;//商品所属者
    private List<String> img_urls;//商品图片
    private String create_time;//商品创建时间
    private String donate_time;//商品捐赠时间
    private int status;//商品状态。0:在售，1:售出,2:捐赠
    private List<User> wait_trades;//商品待交易者名单
    private User trade;//商品交易者
}
