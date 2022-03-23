package cn.xchub.web.reader.service;

import cn.xchub.web.reader.entity.ReaderParam;
import cn.xchub.web.reader.entity.Reader;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ReaderService extends IService<Reader> {
    IPage<Reader> getList(ReaderParam param);
    // 新增读者
    void saveReader(Reader reader);
    // 编辑读者
    void editReader(Reader reader);

    Reader loadByUsername(String username);
}
