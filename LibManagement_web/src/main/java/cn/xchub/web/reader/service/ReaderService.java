package cn.xchub.web.reader.service;

import cn.xchub.web.reader.entity.ReaderParm;
import cn.xchub.web.reader.entity.Reader;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ReaderService extends IService<Reader> {
    IPage<Reader> getList(ReaderParm parm);
    // 新增读者
    void saveReader(Reader reader);
    // 编辑读者
    void editReader(Reader reader);

    Reader loadByUsername(String username);
}
