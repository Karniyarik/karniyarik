package com.karniyarik.brands.nb.io;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.karniyarik.brands.nb.BrandHolder;

public interface BrandsFileService {

	public void exportBrandHolders(List<BrandHolder> brandHolderList, File file) throws Exception;

	public List<BrandHolder> importBrandHolders(InputStream is) throws Exception;

}
