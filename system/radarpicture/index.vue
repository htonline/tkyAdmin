<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">ID</label>
        <el-input v-model="query.id" clearable placeholder="ID" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">文件名称</label>
        <el-input v-model="query.fileName" clearable placeholder="文件名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">文件路径</label>
        <el-input v-model="query.path" clearable placeholder="文件路径" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">创建时间</label>
        <el-input v-model="query.createTime" clearable placeholder="创建时间" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">对应的雷达id</label>
        <el-input v-model="query.radarId" clearable placeholder="对应的雷达id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="ID" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="文件名称">
            <el-input v-model="form.fileName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="文件路径">
            <el-input v-model="form.path" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建时间">
            <el-input v-model="form.createTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="对应的雷达id">
            <el-input v-model="form.radarId" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" />
        <el-table-column prop="fileName" label="文件名称" />
        <el-table-column prop="path" label="文件路径" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column prop="radarId" label="对应的雷达id" />
        <el-table-column v-if="checkPer(['admin','radarPicture:edit','radarPicture:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudRadarPicture from '@/api/radarPicture'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, fileName: null, path: null, createTime: null, radarId: null }
export default {
  name: 'RadarPicture',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'api/radarpicture', url: 'api/radarPicture', idField: 'id', sort: 'id,desc', crudMethod: { ...crudRadarPicture }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'radarPicture:add'],
        edit: ['admin', 'radarPicture:edit'],
        del: ['admin', 'radarPicture:del']
      },
      rules: {
        id: [
          { required: true, message: 'ID不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'id', display_name: 'ID' },
        { key: 'fileName', display_name: '文件名称' },
        { key: 'path', display_name: '文件路径' },
        { key: 'createTime', display_name: '创建时间' },
        { key: 'radarId', display_name: '对应的雷达id' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
