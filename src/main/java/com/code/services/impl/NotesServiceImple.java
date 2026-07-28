package com.code.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.code.dto.NotesDTO;
import com.code.dto.NotesDTO.CategoryDTO;
import com.code.dto.NotesResponse;
import com.code.entity.FileDetails;
import com.code.entity.Notes;
import com.code.exception.ResourceNotFoundException;
import com.code.respository.CategoryRepository;
import com.code.respository.FileRepository;
import com.code.respository.NotesRepository;
import com.code.services.NotesServices;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImple implements NotesServices{

	@Autowired
	private NotesRepository notesRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Value("${file.upload.path}")
	private String uploadPath;

	private Optional<Notes> byId;
	
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
		
		ObjectMapper ob  = new ObjectMapper();
		NotesDTO notesDto = ob.readValue(notes, NotesDTO.class);
		
		if(!ObjectUtils.isEmpty(notesDto.getId())) {
			updateNote(notesDto,file);
		}
		//validation
		checkCategoryExist(notesDto.getCategory());
		//fileDetails
		FileDetails fileDtls=saveFileDetails(file);
		Notes notesMap = mapper.map(notesDto, Notes.class);
		if(!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);
		}
		else {
			if(ObjectUtils.isEmpty(notesDto.getId())) {
				notesMap.setFileDetails(null);
			}
			
		}
		
		Notes saveNotes = notesRepository.save(notesMap);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}


	private void updateNote(NotesDTO notesDto, MultipartFile file) throws Exception {
		Notes existNotes = notesRepository.findById(notesDto.getId())
				.orElseThrow(()->new ResourceNotFoundException("Invalid notes id"));
		if(ObjectUtils.isEmpty(file)) {
			notesDto.setDetails(mapper.map(existNotes.getFileDetails(),com.code.dto.NotesDTO.FileDetails.class));
		}
	}


	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		if(!file.isEmpty()) {
			FileDetails fileDtls = new FileDetails();
			String originalFilename = file.getOriginalFilename();
			
			fileDtls.setOriginalFileName(originalFilename);
			fileDtls.setDisplayFileName(getDisplayName(originalFilename));
			
			String randomString = UUID.randomUUID().toString();
			String extension = FilenameUtils.getExtension(originalFilename);
			String uploadFileName = randomString+"."+extension;
			
			fileDtls.setUploadFileName(uploadFileName);
			fileDtls.setFileSize(file.getSize());
			
			File saveFile = new File(uploadPath);
			if(!saveFile.exists()) {
				saveFile.mkdir();
			}
			String storePath = uploadPath.concat(uploadFileName);
			fileDtls.setPath(storePath);
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if(upload!=0) {
				FileDetails savefileDtls = fileRepository.save(fileDtls);
				return savefileDtls;
			}	
		}
		return null;
	}

	private String getDisplayName(String originalFilename) {
		String extension = FilenameUtils.getExtension(originalFilename);
		String removeExtension = FilenameUtils.removeExtension(originalFilename);
		
		if(removeExtension.length()>8) {
			removeExtension = removeExtension.substring(0,7);
		}
		removeExtension = removeExtension + "."+extension;
		return removeExtension;
	}


	private void checkCategoryExist(CategoryDTO category) throws Exception {
		categoryRepository.findById(category.getId())
						  .orElseThrow(()->new ResourceNotFoundException("Category id invalid"));		
	}


	public List<NotesDTO> getAllNotes() {
		return notesRepository.findAll().stream()
				.map(note -> mapper.map(note, NotesDTO.class)).toList();
	}


	@Override
	public byte[] downloadFile(FileDetails details) throws Exception {
		
		InputStream io = new FileInputStream(details.getPath());
		return StreamUtils.copyToByteArray(io);
	}
	
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails  fileDtls = fileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("file not found with this id"));
		return fileDtls;
		
	}
	
	public NotesResponse getAllNotesByUser(Integer userId,Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo,pageSize);
		
		Page<Notes> pageNotes = notesRepository.findByCreatedBy(userId,pageable);
		
		List<NotesDTO> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDTO.class)).toList();
		
		NotesResponse notesResponse = NotesResponse.builder()
				.notes(notesDto)
				.pageNo(pageNotes.getNumber())
				.pageSize(pageNotes.getSize())
				.totalElements(pageNotes.getTotalElements())
				.totalPages(pageNotes.getTotalPages())
				.isFirst(pageNotes.isFirst())
				.isLast(pageNotes.isLast())
				.build();
		return notesResponse;
	}

}
