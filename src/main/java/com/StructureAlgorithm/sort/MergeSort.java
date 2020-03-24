package com.StructureAlgorithm.sort;

/**
 * @author Administrator
 * date：20180503
 * 简介：归并排序有多路归并排序、两路归并排序 , 可用于内排序，也可以用于外排序。这里仅对内排序的两路归并方法进行讨论。
 *           分而治之(divide - conquer);每个递归过程涉及三个步骤
                     第一, 分解: 把待排序的 n 个元素的序列分解成两个子序列, 每个子序列包括 n/2 个元素.
                     第二, 治理: 对每个子序列分别调用归并排序MergeSort, 进行递归操作
                     第三, 合并: 合并两个排好序的子序列,生成排序结果.
 *
 */
public class MergeSort {
       /**
        * if( hi - lo < 2) return ;           --> tsinghua 教程中的内容是[ lo , hi ) 所以 
        * int mi = (lo + hi) >> 1 ;
        * mergeSort(lo,mi); 对前半段排序  最终处理结果就是递归基，单个元素   调用至return 即退出方法 即退出该递归方法。
        * mergeSort(mi,hi); 对后半段排序  最终处理结果就是递归基，单个元素
        * merge(lo , mi , hi);
        */
	public  static void sort(int[] array , int  low , int  high){ 
		          int mid = ( low + high )/2 ;          //low和high都是索引下标
		    //      if(　hi - low >２){                                                //[ lo , hi )
		        	    sort(array , low , mid) ; 
		        	    sort(array , mid + 1 , high);
		          }
		          
//	}
	public  static void merge(){}
	
	public static void main(String[] args) {
		  //sort() ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
