<template>
    <div class="search-div">
        <!-- template部分修改内容 -->
        <!-- 搜索表单 -->
        <el-form label-width="70px" size="small">
            <el-form-item label="角色名称">
                <el-input
                        v-model="queryDto.roleName"
                        style="width: 100%"
                        placeholder="角色名称"
                        ></el-input>
            </el-form-item>
            <el-row style="display:flex">
                <el-button type="primary" size="small" @click="searchSysRole">
                    搜索
                </el-button>
                <el-button size="small" @click="resetData">重置</el-button>
            </el-row>
        </el-form>

        <!-- 添加按钮 -->
        <div class="tools-div">
            <el-button type="success" size="small" @click="addShow">添 加</el-button>
        </div>
        <!-- 添加角色表单对话框 -->
        <el-dialog v-model="dialogVisible" title="添加或修改角色" width="30%">
            <el-form label-width="120px">
                <el-form-item label="角色名称">
                    <el-input v-model="sysRole.roleName"/>
                </el-form-item>
                <el-form-item label="角色Code">
                    <el-input  v-model="sysRole.roleCode"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">提交</el-button>
                    <el-button @click="dialogVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!--- 角色表格数据 -->
        <el-table :data="list" style="width: 100%">
            <el-table-column prop="roleName" label="角色名称" width="180" />
            <el-table-column prop="roleCode" label="角色code" width="180" />
            <el-table-column prop="createTime" label="创建时间" />
            <el-table-column label="操作" align="center" width="280" #default="scope">
                <el-button type="primary" size="small" @click="editShow(scope.row)">
                    修改
                </el-button>
                <el-button type="danger" size="small">
                    删除
                </el-button>
            </el-table-column>
        </el-table>

        <!--分页条-->
        <!-- <el-pagination
               v-model:current-page="pageParams.page"
               v-model:page-size="pageParams.limit"
               :page-sizes="[10, 20, 50, 100]"
               @size-change="fetchData"
               @current-change="fetchData"
               layout="total, sizes, prev, pager, next"
               :total="total"
        /> -->
        <el-pagination
            v-model="pageParams.page" 
            :current-page="pageParams.page"   
            :page-size="pageParams.limit"      
            :page-sizes="[10, 20, 50, 100]"
            @size-change="fetchData"
            @current-change="fetchData"
            @update:page-size="newPageSize => pageParams.limit = newPageSize" 
            @update:current-page="newPageCur => pageParams.page = newPageCur" 
            layout="total, sizes, prev, pager, next"
            :total="total"
            />
            
  </div>

</template>

<script setup>
import {ref,onMounted} from 'vue'
import {GetSysRoleListByPage,SaveSysRole,UpdateSysRole} from '@/api/sysRole'
import { ElMessage } from 'element-plus'

const roleForm={
    id:"",
    roleName:"",
    roleCode:""
}
const sysRole=ref(roleForm)
//角色添加功能
const dialogVisible=ref(false)

//弹出框进行数据回显
const editShow=(row)=>{//row是传回来的每一行数据
    //对象拓展运算符
    sysRole.value={...row}//值的复制
    dialogVisible.value=true
}
//点击添加弹出框

const addShow=()=>{
    sysRole.value={}
    dialogVisible.value=true
}

//添加或修改方法
//有id值就是修改，没有id值就是添加
const submit=async()=>{
    if(!sysRole.value.id)
{
    const {code}=await SaveSysRole(sysRole.value)
    if(code===200)
    {
        //关闭弹窗
        dialogVisible.value=false
        //提示信息
        ElMessage.success("操作成功")
        //刷新页面
        fetchData()
    }
}
    else{
        const{code}=await UpdateSysRole(sysRole.value)
        if(code===200)
        {
            //关闭弹窗
            dialogVisible.value=false
            //提示信息
            ElMessage.success("操作成功")
            //刷新页面
            fetchData()
        }
    }

}
//角色列表
//定义数据模型
let list=ref([]) //角色列表
let total=ref(0)//总记录数
//分页数据
const pageParamsForm={
    page:1,
    limit:3,
}
const pageParams=ref(pageParamsForm)

const queryDto=ref({"roleName":""})//条件封装数据

//钩子函数
onMounted(()=>{
    fetchData()
})
//操作方法:列表和搜索方法
//列表方法：axios请求调用接口得到列表数据
const fetchData=async()=>{
    console.log("current"+pageParams.value.page)
    const{data,code,message}=await GetSysRoleListByPage(pageParams.value.page,pageParams.value.limit,queryDto.value)
    list.value=data.list
    total.value=data.total
}

const searchSysRole=()=>{
    fetchData()
}

</script>

<style scoped>

.search-div {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}

.tools-div {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}

</style>