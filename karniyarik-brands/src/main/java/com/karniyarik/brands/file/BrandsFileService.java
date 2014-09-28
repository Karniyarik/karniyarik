package com.karniyarik.brands.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.karniyarik.brands.BrandHolder;

public interface BrandsFileService {

	public void exportBrandHolders(List<BrandHolder> brandHolderList, File file)
			throws IOException, IllegalArgumentException;

	public List<BrandHolder> importBrandHolders(InputStream is) throws IOException,
			IllegalArgumentException;

}
