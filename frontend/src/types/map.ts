export interface Position {
  lat: number;
  lon: number;
}

export interface HospitalItem {
  id: number;
  pos: Position;
  response?: boolean; //구급대원용 wait | accept | refuse
}

export interface MapProps {
  type: string; // guest | paramedic | request | transfer
  pos?: Position; // { lat: number; lon: number; }
  hosList?: HospitalItem[];
  paraRequestList?: ParaRequestItem[]; //병원 요청용
  paraTransferList?: HospitalTransferItem[]; //병원 이송용
}

export interface GuestMapProps {
  mapProps: MapProps | undefined;
  setMapProps: React.Dispatch<React.SetStateAction<MapProps | undefined>>;
}

// "ktas": "KTAS1",   // KTAS1, KTAS2, KTAS3, KTAS4, KTAS5
// "ageGroup": "INFANT",   // INFANT, CHILD, ADOLESCENT, YOUTH, MIDDLE, SENIOR
// "gender": "MALE",    // MALE, FEMALE

// //구급대원 데이터
// export interface ParamedicItem {
//   id: number;
//   addr: string;
//   pos: Position;
//   ktas: string;
//   elapseMin: number;
//   leftTime: number;
//   dist: number;
//   paraType: string;
//   paraTag: string[];
//   paraInfo: string;
//   requestAt?: string;
// }

//구급대원 요청(서버)
export interface ParaRequestItem {
  id: number;
  createdAt: string;
  occurrenceId: number;
  memberId: number;
  ktas: string;
  ageGroup: string;
  gender: string;
  description: string;
  latitude: number;
  longitude: number;
  address: string;
  tags: string[];
  files: string[];
  distance: number;
  duration: number;
}

//병원 응답 http post
export interface HospitalResponsePostProps {
  id: number;
  responseType: boolean; //true:수락, false:거절
}

// 구급대원 응답(서버)
export interface HospitalResponseItem {
  id: number;
  responseAt: string;
  responseType: boolean; //true:수락, false:거절
}

// 실시간 위치 + 구급대원 정보 (프론트에서 관리하는 객체)
export interface HospitalTransferItem {
  id: number;
  state: string; // transfer, complete, cancel, wait
  curLat?: number;
  curLon?: number;
  curAddr?: number;
  leftTime?: number;
  leftDist?: number;
  data: ParaRequestItem;
}

// 실시간 위치 (웹소켓으로 받는 객체)
export interface HospitalTransferParaItem {
  id: number;
  state: string; // transfer, complete, cancel, wait
  curLat: number;
  curLon: number;
  curAddr: number;
  leftTime: number;
  leftDist: number;
}
