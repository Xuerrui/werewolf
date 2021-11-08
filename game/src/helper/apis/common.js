import $ajax from '../ajax'
import $util from '../util'

export default {
  getPositionByLocation(data) {
    return $ajax.get('https://apis.map.qq.com/ws/geocoder/v1/', data)
  }
}
