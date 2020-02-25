package com.kk.hub.webservice

import com.kk.hub.common.config.AppConfig
import com.kk.hub.model.bean.CommitsComparison
import com.kk.hub.model.bean.RepoCommit
import com.kk.hub.model.bean.RepoCommitExt
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
 * Created by kk on 2019/10/29  16:26
 */
interface CommitService {

    @GET("repos/{owner}/{repo}/commits")
    fun getRepoCommits(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("owner") owner: String,
        //SHA or branch to start listing commits from. Default: the repository’s default branch (usually master).
        @Query("page") page: Int,
        @Query("sha") branch: String = "master",
        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<retrofit2.Response<ArrayList<RepoCommit>>>


    @GET("repos/{owner}/{repo}/commits/{sha}")
    fun getCommitInfo(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("sha") sha: String
    ): Observable<Response<RepoCommitExt>>


    @GET("repos/{owner}/{repo}/commits/{ref}/comments")
    fun getCommitComments(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Path("ref") ref: String,
        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<RepoCommit>>>


    @GET("repos/{owner}/{repo}/compare/{before}...{head}")
    fun compareTwoCommits(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("before") before: String,
        @Path("head") head: String
    ): Observable<Response<CommitsComparison>>

}