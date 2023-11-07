import { UploadFileApi, privateApi } from './';

// 태그들 조회
export const getTags = async () => {
  try {
    const res = await privateApi.get(`/calling/tag`);
    console.log('getTags then', res.data)
    return res.data;
  } catch (err) {
    console.log('getTags catch', err);
  }
};

// 태그 추가
export const addTag = async (data: any) => {
  try {
    const res = await privateApi.post(`/calling/tag`, data);
    console.log('addTag then', res.data)
    return res.data;
  } catch (err) {
    console.log('addTag catch', err);
  }
};

// 태그 식제
export const deleteTag = async (tagId: number) => {
  try {
    const res = await privateApi.delete(`/calling/tag/${tagId}`,);
    console.log('deleteTag then', res.data)
    return res.data;
  } catch (err) {
    console.log('deleteTag catch', err);
  }
};

// 사고 저장
export const addCalling = async (data: any) => {
  try {
    const res = await privateApi.post(`/calling`, data);
    console.log('addCalling then', res.data)
    return res.data;
  } catch (err) {
    console.log('addCalling catch', err);
  }
};

// 병원들 조회
export const getHospitals = async (data: any) => {
  try {
    const res = await privateApi.post(`/calling/hospital`, data);
    console.log('getHospitals then', res.data)
    return res.data;
  } catch (err) {
    console.log('getHospitals catch', err);
  }
};

// 사진, 동영상 파일 업로드 
export const postCameraUpload = async (info: File[]) => {
  const data = new FormData();
  data.append('files', info[0])
  try {
    const res = await UploadFileApi.post(`/calling/upload`,data);
    console.log('postFileUpload', res.data[0].filePath)
    return res.data[0].filePath;
  } 
  catch (err) {
    console.log('postFileUpload 실패', err);
  }
};

// 음성 파일 업로드 
export const postVoiceUpload = async (info:FormData) => {
  try {
    const res = await UploadFileApi.post(`/calling/upload`,info);
    console.log('postFileUpload', res.data[0].filePath)
    return res.data[0].filePath;
  } 
  catch (err) {
    console.log('postFileUpload 실패', err);
  }
};