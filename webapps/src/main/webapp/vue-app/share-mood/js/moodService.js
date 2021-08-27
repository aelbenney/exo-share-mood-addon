export function getMood(username) {
  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/shareMood/loadToday?username=${ username }`, {
    method: 'GET',
    credentials: 'include',
  }).then((resp) => {
    if (resp && resp.ok) {
      return resp.json();
    } else {
      return null;
    }
  });
}

export function saveMood(emoticon) {
  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/shareMood?mood=${emoticon}`, {
    method: 'POST' ,
    credentials: 'include',
  }).then((resp) => {
    if (resp && resp.ok) {
      return resp.json();
    } else {
      throw new Error('Error updating event');
    }
  });
}
export function updateMood(emoticon) {
  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/shareMood?mood=${emoticon}`, {
    method: 'PUT' ,
    credentials: 'include',
  }).then((resp) => {
    if (resp && resp.ok) {
      return resp.json();
    } else {
      throw new Error('Error updating event');
    }
  });
}
export function loadMoods(username, since) {
  let params = {
    username: username,
    since: since,
  };
  params = $.param(params, true);
  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/shareMood/load?${ params }`, {
    method: 'GET',
    credentials: 'include',
  }).then((resp) => {
    if (resp && resp.ok) {
      return resp.json();
    } else {
      throw new Error('Error retrieving mood');
    }
  });
}
